package com.siqueira.prospect.exceptions;

public class CompanyAlreadyExistsException extends RuntimeException {
    public CompanyAlreadyExistsException(String message) {
        super(message);
    }
}
