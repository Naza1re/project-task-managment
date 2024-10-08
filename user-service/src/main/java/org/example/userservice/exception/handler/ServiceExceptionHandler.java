package org.example.userservice.exception.handler;

import org.example.userservice.exception.NotFoundException;
import org.example.userservice.exception.UserAlreadyExistByPhoneException;
import org.example.userservice.exception.UserNotFoundException;
import org.example.userservice.exception.error.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, NotFoundException.class})
    public ResponseEntity<AppError> handleNotFoundException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(UserAlreadyExistByPhoneException.class)
    public ResponseEntity<AppError> handleConflictException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }
}
