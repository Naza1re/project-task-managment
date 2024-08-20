package org.example.taskservice.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String s) {
        super(s);
    }
}
