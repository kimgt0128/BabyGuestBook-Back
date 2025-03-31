package com.example.guestbook.global.error.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends GuestBookException {
    public BadRequestException(BaseError error) {
        super(error, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(BaseError error, String message) {
        super(error, HttpStatus.BAD_REQUEST, message);
    }
}
