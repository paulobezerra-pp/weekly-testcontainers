package com.picpay.weeklytestcontainers.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
