package com.example.restreword.security;


import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.utils.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseTemplate<Object> responseTemplate = new ResponseTemplate<>(false, accessDeniedException.getMessage(), null);
        AppUtil.writeResponse(response, responseTemplate, HttpStatus.FORBIDDEN);
    }
}


