package com.example.guestbook.global.error.exception;

import com.example.guestbook.global.error.BaseError;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GuestBookException {
    public UnauthorizedException(BaseError error) {
        super(error, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(BaseError error, String message) {
        super(error, HttpStatus.UNAUTHORIZED, message);
    }
}
