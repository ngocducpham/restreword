package com.example.restreword.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.restreword.common.ResponseTemplate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseTemplate<Object> responseTemplate = new ResponseTemplate(false, failed.getMessage(), null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        response.setHeader("Content-Type", "application/json");
        objectMapper.writeValue(response.getWriter(), responseTemplate);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = authentication.getPrincipal() instanceof User ? (User) authentication.getPrincipal() : null;
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        ResponseTemplate<Map<String, String>> responseTemplate = new ResponseTemplate(true, "OK", tokens);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), responseTemplate);

//        response.setHeader("Access-Token", accessToken);
//        response.setHeader("Refresh-Token", refreshToken);

//        Cookie cookie1 = new Cookie("refreshToken", refreshToken);
//        cookie1.setHttpOnly(true);
//        cookie1.setMaxAge(30 * 60);
//        Cookie cookie2 = new Cookie("accessToken", accessToken);
//        cookie2.setHttpOnly(true);
//        cookie2.setMaxAge(10 * 60);
//        response.addCookie(cookie1);
//        response.addCookie(cookie2);
    }
}
