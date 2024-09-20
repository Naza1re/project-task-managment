package org.example.taskservice.exception.handler;

import org.example.taskservice.exception.*;
import org.example.taskservice.exception.error.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {
    @ExceptionHandler({TaskNotFoundException.class, NotFoundException.class})
    public ResponseEntity<AppError> handleNotFoundException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(ProjectClosedException.class)
    public ResponseEntity<AppError> handlerConflictException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    public ResponseEntity<AppError> handlerIllegalAccessException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<AppError> handleServiceCommunicationException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

}
