package com.example.restreword.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.restreword.common.ResponseTemplate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
            filterChain.doFilter(request, response);
        }else {
            String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                ResponseTemplate responseTemplate = new ResponseTemplate(false, "FORBIDDEN", null);
                response.setContentType("application/json");
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                objectMapper.writeValue(response.getWriter(), responseTemplate);
            }else {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(decodedJWT.getClaim("roles").asArray(String.class))
                            .forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                }catch (Exception e) {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    ResponseTemplate responseTemplate = new ResponseTemplate(false, e.getMessage(), null);
                    response.setContentType("application/json");
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                    objectMapper.writeValue(response.getWriter(), responseTemplate);
                }
            }
        }
    }
}
