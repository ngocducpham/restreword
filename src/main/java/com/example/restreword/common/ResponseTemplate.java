package com.example.restreword.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ResponseTemplate<T> {
    private Boolean result;
    private String message;
    private T data;
}

