package org.example.taskservice.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String format) {
        super(format);
    }
}
