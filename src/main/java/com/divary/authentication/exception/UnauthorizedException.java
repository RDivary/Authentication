package com.divary.authentication.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ErrorException{

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
