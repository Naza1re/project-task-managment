package org.example.projectservice.exception.handler;

import org.example.projectservice.exception.NotFoundException;
import org.example.projectservice.exception.ProjectNotFoundException;
import org.example.projectservice.exception.ServiceUnAvailableException;
import org.example.projectservice.exception.error.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler({NotFoundException.class, ProjectNotFoundException.class})
    public ResponseEntity<AppError> handlerNotFoundException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

    @ExceptionHandler({ServiceUnAvailableException.class})
    public ResponseEntity<AppError> handlerServiceUnavailableException(
            RuntimeException exception
    ) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(AppError.builder()
                        .message(message)
                        .build());
    }

}
