package com.example.guestbook.global.error.exception;

import com.example.guestbook.global.error.BaseError;
import org.springframework.http.HttpStatus;

public class UnsupportedMediaTypeException extends GuestBookException{
    public UnsupportedMediaTypeException(BaseError error) {
        super(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public UnsupportedMediaTypeException(BaseError error, String message) {
        super(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
    }
}
