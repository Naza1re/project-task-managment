package org.example.projectservice.exception;

public class ServiceUnAvailableException extends RuntimeException {
    public ServiceUnAvailableException(String format) {
        super(format);
    }
}
