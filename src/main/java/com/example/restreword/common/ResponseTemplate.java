package com.example.restreword.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResponseTemplate<T> {
    private Boolean result;
    private String message;
    private T data;

    public static <T> ResponseEntity<Object> success(T data) {
        return new ResponseEntity<>(new ResponseTemplate<>(true, "OK", data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Object> success(T data, HttpHeaders headers) {
        return new ResponseEntity<>(new ResponseTemplate<>(true, "OK", data), headers, HttpStatus.OK);
    }

    public static <T> ResponseEntity<Object> success(HttpHeaders headers) {
        return new ResponseEntity<>(new ResponseTemplate<>(true, "OK", null), headers, HttpStatus.OK);
    }

    public static ResponseEntity<Object> success() {
        return new ResponseEntity<>(new ResponseTemplate<>(true, "OK", null), HttpStatus.OK);
    }

    public static ResponseEntity<Object> fail(String message, HttpStatus statusCode) {
        return new ResponseEntity<>(new ResponseTemplate<>(false, message, null), statusCode);
    }
}

