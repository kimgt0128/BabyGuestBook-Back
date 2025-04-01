package com.example.guestbook.global.dto;

import com.example.guestbook.global.error.BaseError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String code;
    private int statusCode;
    private String message;
    private T data;

    private final static String SUCCESS_CODE = "SUCCESS";

    public static ApiResponse<Object> success(String message) {
        return ApiResponse.builder()
                .code(SUCCESS_CODE)
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
                .code(SUCCESS_CODE)
                .statusCode(httpStatus.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> fail(BaseError error) {
        return ApiResponse.<T>builder()
                .code(error.getCode())
                .statusCode(error.getHttpStatus().value())
                .message(error.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> fail(BaseError error, String message) {
        return ApiResponse.<T>builder()
                .code(error.getCode())
                .statusCode(error.getHttpStatus().value())
                .message(message)
                .build();
    }
}