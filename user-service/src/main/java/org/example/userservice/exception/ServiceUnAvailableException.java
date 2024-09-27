package org.example.userservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String format) {
        super(format);
    }
}
