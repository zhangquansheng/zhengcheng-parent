package com.zhengcheng.cache.j2cache;

import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;

import static com.zhengcheng.cache.j2cache.J2CacheConstants.J2CACHE_CHANNEL;

/**
 * J2CachePublisher 发布消息
 *
 * @author quansheng1.zhang
 * @since 2023/11/6 16:54
 */
@Slf4j
public class J2CachePublisher {

    private final StringRedisTemplate redisTemplate;

    public J2CachePublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String message) {
        redisTemplate.convertAndSend(J2CACHE_CHANNEL, message);

        log.info("J2CachePublisher publish channel:[{}] message:[{}]", J2CACHE_CHANNEL, message);
    }

}
