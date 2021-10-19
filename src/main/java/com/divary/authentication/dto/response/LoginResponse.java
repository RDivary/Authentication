package com.divary.authentication.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse<T>{

    private boolean otp;

    private T response;
}
