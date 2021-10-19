package com.divary.authentication.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AboutResponse {

    private String username;

    private String fullName;

    private String phoneNumber;
}
