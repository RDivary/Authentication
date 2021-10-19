package com.divary.authentication.controller;

import com.divary.authentication.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity<BaseResponse<?>> getResponseOk(Object data, String message){

        return ResponseEntity.status(HttpStatus.OK).body(getResponse(data, message, HttpStatus.OK));

    }

    protected ResponseEntity<BaseResponse<?>> getResponseCreated(Object data, String message){

        return ResponseEntity.status(HttpStatus.CREATED).body(getResponse(data, message, HttpStatus.CREATED));

    }

    protected BaseResponse<?> getResponse(Object data, String message, HttpStatus httpStatus){

        return BaseResponse.builder()
                .data(data)
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .build();
    }
}
