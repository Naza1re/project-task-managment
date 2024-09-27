package org.example.userservice.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String format) {
        super(format);
    }
}
