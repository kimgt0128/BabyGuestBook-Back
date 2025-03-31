
package com.example.guestbook.global.error;

import com.example.guestbook.global.error.exception.GlobalErrorCode;
import com.example.guestbook.global.error.exception.GuestBookException;
import com.example.guestbook.global.error.exception.BaseError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handle404(NoHandlerFoundException e) {
        log.info(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.create(GlobalErrorCode.NOT_FOUND));
    }

    @ExceptionHandler(GuestBookException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(GuestBookException e) {
        log.info("GuestBookException Exception: {}", e.getMessage());

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.create(e.getError()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.info("Validation Exception: {}", e.getMessage());

        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getField() + ": " + error.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.create(GlobalErrorCode.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        log.error("Unhandled Exception: ", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.create(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Getter
    @NoArgsConstructor
    static class ErrorResponse {
        String code;
        int statusCode;
        String message;

        public static ErrorResponse create(BaseError baseError) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.code = baseError.getCode();
            errorResponse.statusCode = baseError.getHttpStatus().value();
            errorResponse.message = baseError.getMessage();
            return errorResponse;
        }

        public static ErrorResponse create(BaseError baseError, String message) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.code = baseError.getCode();
            errorResponse.statusCode = baseError.getHttpStatus().value();
            errorResponse.message = message;
            return errorResponse;
        }
    }
}
