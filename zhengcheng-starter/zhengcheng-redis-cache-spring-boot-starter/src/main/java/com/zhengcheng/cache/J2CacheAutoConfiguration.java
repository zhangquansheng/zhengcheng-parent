package com.zhengcheng.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.zhengcheng.cache.j2cache.CacheChannel;
import com.zhengcheng.cache.j2cache.DefaultCacheKeyBuilder;
import com.zhengcheng.cache.j2cache.J2CachePublisher;
import com.zhengcheng.cache.j2cache.J2CacheRedisKeyExpirationListener;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import lombok.extern.slf4j.Slf4j;

import static com.zhengcheng.cache.j2cache.J2CacheConstants.J2CACHE_CHANNEL;


/**
 * J2Cache 二级缓存自动配置
 *
 * @author quansheng1.zhang
 * @since 2024/2/5 10:49
 */
@Slf4j
@ComponentScan("com.zhengcheng.cache.j2cache.aspect")
@Configuration
public class J2CacheAutoConfiguration {

    public J2CacheAutoConfiguration() {
        log.info("------ 二级缓存：第一级缓存使用Caffeine,第二级缓存使用 Redis -----------------------------");
        log.info("------ 提供 @Cache @CacheDelete 注解，更方便使用二级缓存 ----------------------------------");
    }

    @ConditionalOnBean(BeanFactory.class)
    @Bean
    public DefaultCacheKeyBuilder defaultCacheKeyBuilder(BeanFactory beanFactory) {
        return new DefaultCacheKeyBuilder(beanFactory);
    }

    @ConditionalOnBean(RedisMessageListenerContainer.class)
    @Bean
    public J2CacheRedisKeyExpirationListener j2CacheRedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        return new J2CacheRedisKeyExpirationListener(listenerContainer);
    }

    /**
     * 二级缓存消息发布者
     */
    @ConditionalOnBean(StringRedisTemplate.class)
    @Bean
    public J2CachePublisher j2CachePublisher(StringRedisTemplate stringRedisTemplate) {
        return new J2CachePublisher(stringRedisTemplate);
    }

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(J2CACHE_CHANNEL); // 定义消息队列的通道名称
    }

    /**
     * 二级缓存
     */
    @ConditionalOnBean({J2CachePublisher.class, Cache.class})
    @Bean
    public CacheChannel cacheChannel(RedisTemplate<String, String> redisTemplate, J2CachePublisher publisher, Cache<String, String> caffeine) {
        return new CacheChannel(redisTemplate, publisher, caffeine);
    }
}
