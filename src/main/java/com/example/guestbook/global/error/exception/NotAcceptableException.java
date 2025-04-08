package com.example.guestbook.global.error.exception;

import com.example.guestbook.global.error.BaseError;
import org.springframework.http.HttpStatus;

public class NotAcceptableException extends GuestBookException {

    protected NotAcceptableException(BaseError error) {
        super(error, HttpStatus.NOT_ACCEPTABLE);
    }

    protected NotAcceptableException(BaseError error, String message) {
        super(error, HttpStatus.NOT_ACCEPTABLE, message);
    }
}
