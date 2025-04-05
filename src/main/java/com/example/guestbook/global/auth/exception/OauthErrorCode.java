package com.example.guestbook.global.auth.exception;

import com.example.guestbook.global.error.BaseError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OauthErrorCode implements BaseError {
    INVALID_REGISTERID("L_001","옳바르지 않은 register_id 입니다",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("L_002","유효하지 않은 토큰 값입니다.",HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("L_003","만료된 토큰입니다",HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN("L_004","지원되지 않는 토큰 형식입니다",HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
