package com.example.guestbook.global.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String statusCode;
    private String message;
    private T data;
    private HttpStatus httpStatus;
    private String code;

    private final static String SUCCESS_CODE = "200";
    private final static String SUCCESS_CREATIONCODE ="201";
    private final static String SUCCESS_NOCOTENTCODE = "204";


    public static <T> ApiResponse<T> success(T data, String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .code("SUCCESS")
                .statusCode(SUCCESS_CODE)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success_creation(T data, String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .code("SUCCESS")
                .statusCode(SUCCESS_CREATIONCODE)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success_noContent(T data, String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .code("SUCCESS")
                .statusCode(SUCCESS_NOCOTENTCODE)
                .message(message)
                .data(data)
                .build();
    }


}