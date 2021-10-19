package com.divary.authentication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {

    private String token;
    private final String type = "Bearer";
    private String username;

}
