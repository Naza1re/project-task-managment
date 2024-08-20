package org.example.taskservice.exception;

public class ProjectClosedException extends RuntimeException {
    public ProjectClosedException(String s) {
        super(s);
    }
}
