package com.divary.authentication.service;

import com.divary.authentication.dto.OTPRabbitmq;
import com.divary.authentication.dto.OTPRedis;
import com.divary.authentication.dto.request.LoginOtp;
import com.divary.authentication.exception.UnauthorizedException;
import com.divary.authentication.preference.ConfigPreference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OTPService {

    private ConfigPreference preference;

    private RedisService redisService;

    private ObjectMapper objectMapper;

    private RabbitTemplate rabbitTemplate;

    private final String OTP_REDIS = "otp";

    private final String WHATSAPP = "whatsapp:";

    private final String FORMAT_WA_OTP = "Jangan beri tahu kode OTP kamu pada siapa pun, kode ini berlaku 3 menit. Kode OTP: %s";

    public OTPService(ConfigPreference preference, RedisService redisService, ObjectMapper objectMapper, RabbitTemplate rabbitTemplate) {
        this.preference = preference;
        this.redisService = redisService;
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void generatedOTP(UserDetailsImpl userDetails, String randomString) throws JsonProcessingException {

        Random random = new Random();
        int otp = random.nextInt(8999) + 1000;

        OTPRedis otpRedis = OTPRedis.builder()
                .username(userDetails.getUsername())
                .otp(otp)
                .build();

        OTPRabbitmq otpRabbitmq = OTPRabbitmq.builder()
                .otp(otp)
                .phoneNumber(userDetails.getPhoneNumber())
                .build();

        redisService.set(OTP_REDIS, randomString, objectMapper.writeValueAsString(otpRedis), 180);

        rabbitTemplate.convertAndSend(preference.rabbitmqOtpExchange, preference.rabbitmqRoutingKey, otpRabbitmq);

    }

    public String checkedOTP(LoginOtp form) throws JsonProcessingException {

        String result = redisService.get(OTP_REDIS, form.getRandomString());

        if (Strings.isBlank(result)) throw new UnauthorizedException("Invalid OTP");

        OTPRedis otpRedis = objectMapper.readValue(result, OTPRedis.class);

        if (form.getOtp() != otpRedis.getOtp()) throw new UnauthorizedException("Invalid OTP");

        redisService.delete(OTP_REDIS, form.getRandomString());

        return otpRedis.getUsername();

    }

    public void sendOtp(OTPRabbitmq otpRabbitmq){

        String message = String.format(FORMAT_WA_OTP, otpRabbitmq.getOtp());

        Twilio.init(preference.twilioAccountSid, preference.twilioAuthToken);

        Message
                .creator(new PhoneNumber(WHATSAPP + otpRabbitmq.getPhoneNumber()), new PhoneNumber(WHATSAPP + preference.twilioFromNumberWA), message)
                .create();

    }
}
