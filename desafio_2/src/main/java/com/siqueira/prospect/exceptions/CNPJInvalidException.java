package com.siqueira.prospect.exceptions;

public class CNPJInvalidException extends RuntimeException {
    public CNPJInvalidException(String message) {
        super(message);
    }
}