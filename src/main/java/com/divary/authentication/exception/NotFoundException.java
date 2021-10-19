package com.divary.authentication.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ErrorException{

    public NotFoundException(String message) {

        super(message, HttpStatus.NOT_FOUND);
    }
}
