package com.divary.authentication.event.ampq;

import com.divary.authentication.dto.OTPRabbitmq;
import com.divary.authentication.preference.ConfigPreference;
import com.divary.authentication.service.OTPService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitListenerConfig {

    private static final Logger logger = LogManager.getLogger();

    private RabbitTemplate rabbitTemplate;

    private ConfigPreference preference;

    private ObjectMapper objectMapper;

    private OTPService otpService;

    private static final String DEATH_LETTER_HEADER = "x-death";
    private static final String EXCHANGE = "exchange";
    private static final String ROUTING_KEYS = "routing-keys";
    private static final String REASON = "reason";
    private static final String COUNT = "count";

    private static final String REASON_REJECTED = "rejected";
    private static final String REASON_EXPIRED = "expired";

    public RabbitListenerConfig(RabbitTemplate rabbitTemplate, ConfigPreference preference, ObjectMapper objectMapper, OTPService otpService) {
        this.rabbitTemplate = rabbitTemplate;
        this.preference = preference;
        this.objectMapper = objectMapper;
        this.otpService = otpService;
    }

    @RabbitListener(queues = "${rabbitmq.otp.queue}")
    public void consumeOtp(Message message) throws IOException {

        OTPRabbitmq otpRabbitmq = objectMapper.readValue(message.getBody(), OTPRabbitmq.class);

        logger.info("{} request otp received", otpRabbitmq.getOtp());

        otpService.sendOtp(otpRabbitmq);
    }
}
