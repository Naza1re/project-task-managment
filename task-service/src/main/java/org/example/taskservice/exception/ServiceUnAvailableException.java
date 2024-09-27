package org.example.taskservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String format) {
        super(format);
    }
}
