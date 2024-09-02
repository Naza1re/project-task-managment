package org.example.taskservice.exception;

public class NotFoundException extends RuntimeException  {
    public NotFoundException(String s) {
        super(s);
    }
}
