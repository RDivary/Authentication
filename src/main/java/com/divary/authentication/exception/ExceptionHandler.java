package com.divary.authentication.exception;

import com.divary.authentication.dto.response.BaseResponse;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ErrorException.class})
    public ResponseEntity<BaseResponse<?>> exceptionHandler(ErrorException e){

        return ResponseEntity.status(e.getHttpStatus()).body(getResponseBody(e.getHttpStatus(), e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<BaseResponse<?>> UsernameNotFoundExceptionHandler(UsernameNotFoundException ex){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getResponseBody(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<BaseResponse<?>> BadCredentialsExceptionHandler(BadCredentialsException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getResponseBody(HttpStatus.UNAUTHORIZED, "Sorry, your password was incorrect."));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {PropertyReferenceException.class})
    public ResponseEntity<BaseResponse<?>> PropertyReferenceExceptionHandler(PropertyReferenceException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getResponseBody(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private BaseResponse<?> getResponseBody(HttpStatus httpStatus, String message) {

        return BaseResponse.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .build();

    }
}
