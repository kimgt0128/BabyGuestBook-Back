package com.example.guestbook.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface BaseError {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
