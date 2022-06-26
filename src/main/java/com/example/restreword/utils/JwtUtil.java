package com.example.restreword.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class JwtUtil {
    private static final String secret = "verySecretKey";
    private static final Long expiration = 1000L * 60 * 60 * 24 * 7;

    public static String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public static String getUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secret.getBytes())).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
