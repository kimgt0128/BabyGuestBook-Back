package com.example.guestbook.domain.like.exception;

import com.example.guestbook.global.error.BaseError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements BaseError {
    ALREADY_LIKED("e_001", "이미 좋아요를 누른 상태입니다.", HttpStatus.BAD_REQUEST),
    LIKE_NOT_FOUND("e_002", "좋아요가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND("e_003", "좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
