package com.example.restreword.filter;

import com.example.restreword.common.ResponseTemplate;
import com.example.restreword.utils.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ResponseTemplate<Object> responseTemplate = new ResponseTemplate<>(false, e.getMessage(), null);
            AppUtil.writeResponse(response, responseTemplate, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

