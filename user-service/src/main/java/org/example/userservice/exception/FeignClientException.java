package org.example.userservice.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String s) {
        super(s);
    }
}
