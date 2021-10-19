package com.divary.authentication.config;

import com.divary.authentication.preference.ConfigPreference;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    private ConfigPreference preference;

    public RabbitMQConfig(ConfigPreference preference) {
        this.preference = preference;
    }

    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory(){

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(preference.rabbitmqHost);
        cachingConnectionFactory.setPort(preference.rabbitmqPort);
        cachingConnectionFactory.setUsername(preference.rabbitmqUsername);
        cachingConnectionFactory.setPassword(preference.rabbitmqPassword);

        return cachingConnectionFactory;

    }

    @Bean
    public RabbitTemplate rabbitTemplate(){

        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;

    }

    @Bean
    DirectExchange otpExchange(){
        return new DirectExchange(preference.rabbitmqOtpExchange);
    }

    @Bean
    Queue otpQueue(){
        return QueueBuilder.durable(preference.rabbitmqQueue).build();
    }

    @Bean
    Binding otpBinding(){
        return BindingBuilder.bind(otpQueue()).to(otpExchange()).with(preference.rabbitmqRoutingKey);
    }
}
