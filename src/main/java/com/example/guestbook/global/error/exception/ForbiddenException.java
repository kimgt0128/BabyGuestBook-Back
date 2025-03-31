package com.example.guestbook.global.error.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends GuestBookException {
    public ForbiddenException(BaseError error) {
        super(error, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(BaseError error, String message) {
        super(error, HttpStatus.FORBIDDEN, message);
    }
}
