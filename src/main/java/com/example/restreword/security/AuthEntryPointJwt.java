package com.example.restreword.security;

import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.utils.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseTemplate<Object> responseTemplate = new ResponseTemplate<>(false, authException.getMessage(), null);
        AppUtil.writeResponse(response, responseTemplate, HttpStatus.UNAUTHORIZED);
    }
}
