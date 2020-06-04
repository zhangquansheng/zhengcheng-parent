package com.zhengcheng.redis;

import com.zhengcheng.common.lock.DistributedLock;
import com.zhengcheng.redis.lock.RedissonDistributedRLock;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonAutoConfiguration
 *
 * @author :    quansheng.zhang
 * @date :    2020/6/4 22:34
 */
@Configuration
@ConditionalOnClass(Config.class)
public class RedissonAutoConfiguration {

    /**
     * 单机模式自动装配
     *
     * @return RedissonClient
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());

        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * Redisson 分布式锁
     * redisson.lock.enable = true
     *
     * @param redissonClient 客户端
     * @return 分布式锁
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.lock.enable", havingValue = "true")
    DistributedLock distributedLock(RedissonClient redissonClient) {
        return new RedissonDistributedRLock(redissonClient);
    }
}
