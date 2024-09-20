package org.example.taskservice.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException(String format) {
        super(format);
    }
}
