package com.divary.authentication.preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigPreference {

    // REDIS
    @Value("${redis.hostname}")
    public String redisHostname;

    @Value("${redis.database.index}")
    public String redisDatabaseIndex;

    @Value("${redis.port}")
    public String redisPort;

    @Value("${redis.password}")
    public String redisPassword;

    @Value("${spring.rabbitmq.host}")
    public String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    public int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    public String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    public String rabbitmqPassword;

    @Value("${rabbitmq.otp.exchange}")
    public String rabbitmqOtpExchange;

    @Value("${rabbitmq.otp.routingkey}")
    public String rabbitmqRoutingKey;

    @Value("${rabbitmq.otp.queue}")
    public String rabbitmqQueue;

    @Value("${twilio.account.sid}")
    public String twilioAccountSid;

    @Value("${twilio.auth.token}")
    public String twilioAuthToken;

    @Value("${twilio.from.number.wa}")
    public String twilioFromNumberWA;

    @Value("${twilio.from.number.sms}")
    public String twilioFromNumberSMS;
}
