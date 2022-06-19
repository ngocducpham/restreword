package com.example.restreword.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SettingNotFoundException extends RuntimeException{
    public SettingNotFoundException(String message){super(message);}
}
