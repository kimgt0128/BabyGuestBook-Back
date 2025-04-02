package com.example.guestbook.global.auth.exception;

import com.example.guestbook.global.error.BaseError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OauthErrorCode implements BaseError {
    INVALID_REGISTERID("L_001","옳바르지 않은 register_id 입니다",HttpStatus.BAD_REQUEST);
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
