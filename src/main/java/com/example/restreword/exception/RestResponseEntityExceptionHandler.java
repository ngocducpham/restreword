package com.example.restreword.exception;

import com.example.restreword.common.ResponseTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseTemplate<String> responseTemplate;

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ResponseTemplate
                .builder()
                .result(false)
                .message(ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ResponseTemplate
                .builder()
                .result(false)
                .message(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SettingNotFoundException.class)
    public final ResponseEntity<Object> handleSettingNotFoundExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ResponseTemplate
                .builder()
                .result(false)
                .message(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ResponseTemplate
                .builder()
                .result(false)
                .message(ex.getBindingResult().getFieldError().getDefaultMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
