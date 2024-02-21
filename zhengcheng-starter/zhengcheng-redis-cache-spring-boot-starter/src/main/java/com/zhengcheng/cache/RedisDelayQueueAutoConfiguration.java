package com.zhengcheng.cache;

import com.zhengcheng.cache.queue.RedisDelayQueueConsumerExecutor;
import com.zhengcheng.cache.queue.RedisDelayQueueProducer;

import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisDelayQueueAutoConfiguration 延时队列配置
 *
 * @author quansheng1.zhang
 * @since 2023/9/6 16:32
 */
@Slf4j
@AutoConfigureAfter(RedissonAutoConfiguration.class)
@Configuration
public class RedisDelayQueueAutoConfiguration {

    public RedisDelayQueueAutoConfiguration() {
        log.info("------ Redisson 延时队列配置成功 ----------------------------------");
    }

    @Bean
    @ConditionalOnMissingBean(RedisDelayQueueConsumerExecutor.class)
    @ConditionalOnBean(RedissonClient.class)
    public RedisDelayQueueConsumerExecutor redisDelayQueueConsumerExecutor(RedissonClient redissonClient) {
        return new RedisDelayQueueConsumerExecutor(redissonClient);
    }

    @Bean
    @ConditionalOnMissingBean(RedisDelayQueueProducer.class)
    @ConditionalOnBean(RedissonClient.class)
    public RedisDelayQueueProducer redisDelayQueueProducer(RedissonClient redissonClient) {
        return new RedisDelayQueueProducer(redissonClient);
    }

}
