package com.example.restreword.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.entity.Role;
import com.example.restreword.entity.User;
import com.example.restreword.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class PublicController {
    private final UserRepository userRepository;

    @GetMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestHeader HttpHeaders headers) {
        String authorizationHeader = headers.getFirst("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String requestRefreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(requestRefreshToken);
                String username = decodedJWT.getSubject();
                User user = userRepository.findByName(username).orElseThrow(() -> new RuntimeException("User not found"));

                String accessToken = JWT.create()
                        .withSubject(user.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                String refreshToken = JWT.create()
                        .withSubject(user.getName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                return ResponseTemplate.success(tokens);
            } catch (Exception e) {
                return ResponseTemplate.fail(e.getMessage(), HttpStatus.FORBIDDEN);
            }
        } else {
            return ResponseTemplate.fail("Invalid token", HttpStatus.FORBIDDEN);
        }
    }
}
