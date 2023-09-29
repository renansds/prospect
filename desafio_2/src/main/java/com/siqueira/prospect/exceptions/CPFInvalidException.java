package com.siqueira.prospect.exceptions;

public class CPFInvalidException extends RuntimeException {
    public CPFInvalidException(String message) {
        super(message);
    }
}