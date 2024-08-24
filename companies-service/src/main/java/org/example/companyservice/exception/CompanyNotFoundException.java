package org.example.companyservice.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String s) {
        super(s);
    }
}
