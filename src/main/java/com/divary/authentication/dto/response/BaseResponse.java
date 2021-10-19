package com.divary.authentication.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BaseResponse<T> {

    private T data;

    private int code;

    private String status;

    private String message;

//    private final LocalDateTime time = LocalDateTime.now();

}
