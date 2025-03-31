package com.example.guestbook.global.error.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends GuestBookException {
    public InternalServerException(BaseError error) {
        super(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(BaseError error, String message) {
        super(error, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
