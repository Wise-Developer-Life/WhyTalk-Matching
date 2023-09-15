package com.wisedevlife.whytalkmatching.common.utils;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, String> redisTemplate;

    private <T> RedisTemplate<String, T> createTemplate() {
        return new RedisTemplate<>();
    }

    public <T> void setObject(String key, T object) {
        RedisTemplate<String, T> template = createTemplate();
        template.opsForValue().setIfAbsent(key, object);
    }

    public <T> T getObject(String key) {
        return this.<T>createTemplate().opsForValue().get(key);
    }

    public <T> void addValuesIntoSet(String setName, T... value) {
        this.<T>createTemplate().opsForSet().add(setName, value);
    }

    public <T> Set<T> getValuesFromSet(String setName) {
        return this.<T>createTemplate().opsForSet().members(setName);
    }

    public <T> void removeValueFromSet(String setName, T value) {
        this.<T>createTemplate().opsForSet().remove(setName, value);
    }

    public <T> void addValueToSortedSet(String setName, T value, double score) {
        this.<T>createTemplate().opsForZSet().add(setName, value, score);
    }

    public <T> void removeValueFromSortedSet(String setName, T value) {
        this.<T>createTemplate().opsForZSet().remove(setName, value);
    }

    public <T> Set<T> getAllValuesFromSortedSetDesc(String setName) {
        return this.<T>createTemplate().opsForZSet().reverseRange(setName, 0, -1);
    }

    public <T> boolean isSortedSetContainsKey(String setName, T key) {
        return this.<T>createTemplate().opsForZSet().score(setName, key) != null;
    }

    public void setWithExpired(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }

    public void setWithExpired(String key, String value, Duration duration) {
        redisTemplate
                .opsForValue()
                .setIfAbsent(key, value, duration.getSeconds(), TimeUnit.SECONDS);
    }

    public void setMapWithExpired(
            String key, Map<Object, Object> value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, value);
        redisTemplate.opsForValue().getAndExpire(key, timeout, timeUnit);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
}
