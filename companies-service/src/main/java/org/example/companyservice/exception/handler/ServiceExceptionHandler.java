package org.example.companyservice.exception.handler;

import org.example.companyservice.exception.error.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<AppError> handlerNotFoundException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }
}
