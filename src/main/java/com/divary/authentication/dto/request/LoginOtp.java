package com.divary.authentication.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginOtp {

    private String randomString;
    private int otp;
}
