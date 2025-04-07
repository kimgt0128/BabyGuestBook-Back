package com.example.guestbook.domain.member.exception;

import com.example.guestbook.global.error.BaseError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseError {
    INVALID_EMAIL("M_001","옳바르지 않은 email 입니다", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

