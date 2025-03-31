package com.example.guestbook.global.error.exception;

import com.example.guestbook.global.error.BaseError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GuestBookException extends RuntimeException {

    private final BaseError error;
    private final HttpStatus httpStatus;

    protected GuestBookException(BaseError error, HttpStatus httpStatus) {
        super(error.getMessage());
        this.error = error;
        this.httpStatus = httpStatus;
    }

    protected GuestBookException(BaseError error, HttpStatus httpStatus, String message) {
        super(message);
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
