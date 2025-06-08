package org.finalproject.movierenting.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.finalproject.movierenting.entity.Consumer;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode("f671b688de43dd1cf5149bd1fb0b4e85c8fe07a6a25ee1c13c1a5c82ede5d95dcc6ac005319e27f826f74dac8cbc1081623632ea0a4538a71a2d268ac32724d2"));

    public String generateToken(Consumer consumer) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

        return  Jwts.builder()
                .signWith(secretKey)
                .subject(consumer.getEmail())
                .issuer("movie-rental-axz")
                .claim("role", consumer.getPermission())
                .expiration(expiration)
                .compact();
    }
    public Claims validateToken(String token) {

        return Jwts.parser().verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
