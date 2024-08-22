package org.example.companyservice.exception;

public class CompanyAlreadyExistException extends RuntimeException {
    public CompanyAlreadyExistException(String s) {
        super(s);
    }
}
