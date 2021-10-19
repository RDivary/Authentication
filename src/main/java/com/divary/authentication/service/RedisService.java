package com.divary.authentication.service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RedisService {

    private final RedisClient redisClient;

    @Autowired
    public RedisService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public void set(String prefix, String key, String value, Integer expiration){

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommand = connection.sync();
        String pair = prefix + ":" + key;

        syncCommand.set(pair, value);

        if (null != expiration){
            syncCommand.expire(pair, expiration);
        }

        connection.close();

    }

    public boolean addExp(String prefix, String key, Integer expiration){

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommand = connection.sync();
        String pair = prefix + ":" + key;
        boolean result;
        String s = syncCommand.get(pair);

        if (StringUtils.isBlank(s)) {
            result = false;
        } else {
            syncCommand.expire(pair, expiration);
            result = true;
        }

        connection.close();

        return result;

    }

    public List<String> findAll(String prefix){

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommand = connection.sync();
        String pair = prefix + "*";

        List<String> keys = syncCommand.keys(pair);
        List<String> result = new ArrayList<>();

        keys.forEach(key ->
            result.add(syncCommand.get(key))
        );

        connection.close();

        return result;

    }

    public String get(String prefix, String key){

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommand = connection.sync();
        String pair = prefix + ":"  + key;

        String result = syncCommand.get(pair);

        connection.close();

        return result;

    }

    public void delete(String prefix, String key){

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommand = connection.sync();
        String pair = prefix + ":" + key;

        syncCommand.del(pair);

        connection.close();
    }

    public void delete(String prefix){

        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommand = connection.sync();
        String pair = prefix + "*";

        List<String> keys = syncCommand.keys(pair);

        keys.forEach(syncCommand::del);

        connection.close();

    }
}
