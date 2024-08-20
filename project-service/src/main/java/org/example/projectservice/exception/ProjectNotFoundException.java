package org.example.projectservice.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String s) {
        super(s);
    }
}
