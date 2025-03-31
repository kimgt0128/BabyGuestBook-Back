package com.example.guestbook.global.error.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GuestBookException {
    public NotFoundException(BaseError error) {
        super(error, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(BaseError error, String message) {
        super(error, HttpStatus.NOT_FOUND, message);
    }
}
