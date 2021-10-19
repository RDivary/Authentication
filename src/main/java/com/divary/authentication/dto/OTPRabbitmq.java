package com.divary.authentication.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTPRabbitmq {

    private int otp;

    private String phoneNumber;

}
