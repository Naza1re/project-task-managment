package org.example.userservice.exception;

public class UserAlreadyExistByPhoneException extends RuntimeException {
    public UserAlreadyExistByPhoneException(String s) {
        super(s);
    }
}
