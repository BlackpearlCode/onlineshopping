package com.onlineshopping.redis.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisConfig {

    LettuceConnectionFactory redisConnectionFactory();
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory);

}
