package com.example.guestbook.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum GlobalErrorCode implements BaseError {
    BAD_REQUEST("F_001", "FEED_404", HttpStatus.NOT_FOUND),
    NOT_FOUND("F_002", "FEED_404", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("F_001", "FEED_404", HttpStatus.NOT_FOUND);
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
