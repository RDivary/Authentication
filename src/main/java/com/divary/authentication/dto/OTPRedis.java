package com.divary.authentication.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTPRedis {

    private String username;

    private int otp;
}
