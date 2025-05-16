package org.finalproject.movierenting.controller;


import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

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
}
