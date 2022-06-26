package com.example.restreword.controller;

import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.payload.LoginRequest;
import com.example.restreword.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            String jwt = JwtUtil.generateToken(authentication.getName());
            return ResponseTemplate.success(jwt);
        } else {
            return ResponseTemplate.fail("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }
}
