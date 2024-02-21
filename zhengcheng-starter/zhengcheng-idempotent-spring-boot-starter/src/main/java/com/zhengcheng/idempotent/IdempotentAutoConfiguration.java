package com.zhengcheng.idempotent;

import com.zhengcheng.expression.KeyResolver;
import com.zhengcheng.idempotent.aspectj.IdempotentAspect;

import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 幂等功能配置
 *
 * @author zhangquansheng
 */
@Configuration
public class IdempotentAutoConfiguration {

    @ConditionalOnBean({RedissonClient.class, KeyResolver.class})
    @Bean
    public IdempotentAspect idempotentAspect(RedissonClient redissonClient, KeyResolver keyResolver) {
        return new IdempotentAspect(redissonClient, keyResolver);
    }

}
