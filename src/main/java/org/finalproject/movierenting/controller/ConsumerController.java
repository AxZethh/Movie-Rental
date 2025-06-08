package org.finalproject.movierenting.controller;


import org.finalproject.movierenting.dto.ConsumerSignUpDTO;
import org.finalproject.movierenting.dto.LoginForm;
import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.service.ConsumerService;
import org.finalproject.movierenting.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("getConsumers")
    public ResponseEntity<List<Consumer>> getConsumers() {
        List<Consumer> consumers = consumerService.getUsers();
        return ResponseEntity.ok(consumers);
    }

    @GetMapping("getConsumer/{id}")
    public ResponseEntity<Consumer> getConsumer(@PathVariable UUID id) {
        Consumer consumer = consumerService.getUser(id);
        return ResponseEntity.ok(consumer);
    }

    @GetMapping("getConsumer")
    public ResponseEntity<Consumer> getConsumer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(consumerService.getUserByEmail(email));
    }

    @PostMapping("auth/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        Consumer consumer = consumerService.getUserByEmail(loginForm.getEmail());
        if(consumer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        if(!consumer.getPassword().equals(loginForm.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Password!");
        }
        return ResponseEntity.ok(jwtUtil.generateToken(consumer));
    }

    @PostMapping("auth/signup")
    public ResponseEntity<String> signUp(@RequestBody ConsumerSignUpDTO consumer) { // Could leave the body out to make the user(frontend) handle visual parts for errors
        if(consumer.getEmail() == null || consumer.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Password is empty!");
        }
        if(!consumer.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format!");
        }
        if(consumerService.getUserByEmail(consumer.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This email is already taken!");
        }
        consumerService.saveUser(consumer);
        return ResponseEntity.ok("Signup successful!");
    }

}
