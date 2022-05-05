package com.divary.authentication.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
public class BaseResponse<T> {
    private T data;

    private int code;

    private String status;

    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime time = LocalDateTime.now();
}
