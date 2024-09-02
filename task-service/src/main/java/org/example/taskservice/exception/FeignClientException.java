package org.example.taskservice.exception;

public class FeignClientException extends RuntimeException{
    public FeignClientException(String s) {
        super(s);
    }
}
