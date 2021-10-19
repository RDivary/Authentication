package com.divary.authentication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OTPResponse {

    private String randomString;
}
