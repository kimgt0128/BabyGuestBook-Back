package com.example.guestbook.global.error.exception;

import com.example.guestbook.global.error.BaseError;
import org.springframework.http.HttpStatus;

public class ExistingResourceException extends GuestBookException {
    public ExistingResourceException(BaseError error) {
        super(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ExistingResourceException(BaseError error, String message) {
        super(error, HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
