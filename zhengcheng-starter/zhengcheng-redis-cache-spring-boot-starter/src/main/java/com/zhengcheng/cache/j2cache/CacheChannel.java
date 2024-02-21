package com.zhengcheng.cache.j2cache;

import com.github.benmanes.caffeine.cache.Cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * CacheChannel vh自定义的二级缓存类
 *
 * @author quansheng1.zhang
 * @since 2023/11/6 17:01
 */
public class CacheChannel {

    private final RedisTemplate<String, String> redisTemplate;
    private final J2CachePublisher publisher;

    private final Cache<String, String> caffeine;

    public CacheChannel(RedisTemplate<String, String> redisTemplate, J2CachePublisher publisher, Cache<String, String> caffeine) {
        this.redisTemplate = redisTemplate;
        this.publisher = publisher;
        this.caffeine = caffeine;
    }

    /**
     * 读取缓存
     */
    public String get(String key) {
        String value = caffeine.getIfPresent(key);
        if (CharSequenceUtil.isNotBlank(value)) {
            return value;
        }

        value = redisTemplate.opsForValue().get(key);
        if (CharSequenceUtil.isNotBlank(value)) {
            // 设置一级缓存，在分布式微服务的情况下，新上来的服务，没有一级缓存
            caffeine.put(key, value);
        }
        return value;
    }

    /**
     * 放入缓存
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        caffeine.put(key, value);
    }

    /**
     * 放入缓存，并设置有效期（redis自动过期，一级缓存 caffeine 通过 redis 的发布订阅清除）
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
        caffeine.put(key, value);
    }

    /**
     * 删除缓存，并发布广播通知到redis，其他的监听者收到消息后删除本地缓存
     *
     * @param key
     */
    public void delete(String key) {
        //先清比较耗时的二级缓存，再清一级缓存
        redisTemplate.delete(key);
        caffeine.invalidate(key);
        //通知其他的服务，清一级缓存
        publisher.publish(key);
    }

}
