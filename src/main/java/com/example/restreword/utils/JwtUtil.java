package com.example.restreword.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.restreword.entity.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtUtil {
    private static final String secret = "verySecretKey";
    private static final Long expiration = 1000L * 60 * 60 * 24 * 7;

    public static String generateToken(String username, List<String> roles) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(username)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public static String getUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    public static List<String> getRolesFromToken(String token) {
        return JWT.decode(token).getClaim("roles").asList(String.class);
    }

    public static void validateToken(String token) {
        JWT.require(Algorithm.HMAC512(secret.getBytes())).build().verify(token);
    }
}
