package org.finalproject.movierenting.util;


import io.jsonwebtoken.Claims;
import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.enums.ConsumerPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil();
    Consumer consumer = new Consumer();

    @BeforeEach
    public void setUp() {

        consumer.setId(UUID.randomUUID());
        consumer.setFirstName("John");
        consumer.setLastName("Doe");
        consumer.setBonusPoints(100);
        consumer.setEmail("Some@email.com");
        consumer.setPermission(ConsumerPermission.ADMIN);
    }

    @Test
    public void whenGenerateToken_shouldGenerateValidToken() {
        String token = jwtUtil.generateToken(consumer);
        Claims claims = jwtUtil.validateToken(token);

        assertEquals(consumer.getEmail(), claims.getSubject());
        assertEquals(consumer.getPermission().name(), claims.get("role"));
        System.out.println("Email: " + claims.getSubject() + "\nPermission: " +  consumer.getPermission());
    }
}
