package com.zhengcheng.cache;

import com.zhengcheng.common.lock.DistributedLock;
import com.zhengcheng.cache.lock.RedissonDistributedRLock;
import com.zhengcheng.cache.properties.RedissonProperties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Redisson 自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2020/6/4 22:34
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties({RedissonProperties.class})
public class RedissonAutoConfiguration {

    /**
     * 单机模式自动装配
     *
     * @return RedissonClient
     */
    @Primary
    @Bean
    @ConditionalOnProperty(name = "redisson.address")
    RedissonClient redissonSingle(RedissonProperties redissonProperties) {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());

        if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }

        return Redisson.create(config);
    }


    /**
     * 哨兵模式自动装配
     *
     * @return RedissonClient
     */
    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonSentinel(RedissonProperties redissonProperties) {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setTimeout(redissonProperties.getTimeout())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());

        if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
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
