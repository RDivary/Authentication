package com.divary.authentication.config;

import com.divary.authentication.preference.ConfigPreference;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    private ConfigPreference preference;

    public RedisConfig(ConfigPreference preference) {
        this.preference = preference;
    }

    @Bean
    public RedisClient redisClient(){

        RedisURI.Builder redisUriBuilder = RedisURI.builder();

        redisUriBuilder
                .withHost(preference.redisHostname)
                .withPort(Integer.parseInt(preference.redisPort))
                .withPassword(preference.redisPassword.toCharArray())
                .withDatabase(StringUtils.isBlank(preference.redisDatabaseIndex) ? 0 : Integer.parseInt(preference.redisDatabaseIndex));

        return RedisClient.create(redisUriBuilder.build());

    }
}
