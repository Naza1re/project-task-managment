package org.example.projectservice.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String format) {
        super(format);
    }
}
