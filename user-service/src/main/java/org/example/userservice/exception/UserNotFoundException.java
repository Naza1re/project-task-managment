package org.example.userservice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
