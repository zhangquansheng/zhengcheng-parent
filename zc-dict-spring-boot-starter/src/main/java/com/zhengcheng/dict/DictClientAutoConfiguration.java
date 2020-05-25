package com.zhengcheng.dict;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.dict.adminservice.DictAdminService;
import com.zhengcheng.dict.client.DictClient;
import com.zhengcheng.dict.common.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.time.Duration;

/**
 * 数据字典客户端自动配置
 *
 * @author :    zhangquansheng
 * @date :    2020/5/25 14:21
 */
@Slf4j
@Import({DictClient.class, DictAdminService.class, DictUtils.class})
@Configuration
public class DictClientAutoConfiguration {

    @Value("${zc.dict.redis.database}")
    private int database;
    @Value("${zc.dict.redis.timeout}")
    private long timeout;
    @Value("${zc.dict.redis.lettuce.pool.max-active}")
    private int maxActive;
    @Value("${zc.dict.redis.lettuce.pool.max-wait}")
    private int maxWait;
    @Value("${zc.dict.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${zc.dict.redis.lettuce.pool.min-idle}")
    private int minIdle;
    @Value("${zc.dict.redis.host}")
    private String hostName;
    @Value("${zc.dict.redis.port}")
    private int port;
    @Value("${zc.dict.redis.password}")
    private String password;

    /**
     * 消息适配器
     *
     * @param dictClient 接收者(字典客户端)
     * @return MessageListenerAdapter
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(DictClient dictClient) {
        return new MessageListenerAdapter(dictClient, "onMessage");
    }

    @Bean
    public RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(this.getDictConnectionFactory());
        // 可以添加多个 messageListener，配置不同的交换机
        container.addMessageListener(listenerAdapter, new PatternTopic(DictUtils.PATTERN_TOPIC));
        return container;
    }


    @Bean("dictStringRedisTemplate")
    public StringRedisTemplate dictStringRedisTemplate() {
        return new StringRedisTemplate(this.getDictConnectionFactory());
    }

    private RedisConnectionFactory getDictConnectionFactory() {
        /* ========= 基本配置 ========= */
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        configuration.setDatabase(database);
        if (!StrUtil.isEmpty(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            configuration.setPassword(redisPassword);
        }

        /* ========= 连接池通用配置 ========= */
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);

        /* ========= lettuce pool ========= */
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(Duration.ofSeconds(timeout));
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }
}
