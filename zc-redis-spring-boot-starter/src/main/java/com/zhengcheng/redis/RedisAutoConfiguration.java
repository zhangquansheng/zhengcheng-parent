package com.zhengcheng.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置类
 * https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/12 22:41
 */
@Slf4j
@EnableCaching
public class RedisAutoConfiguration extends CachingConfigurerSupport {

    /**
     * RedisTemplate配置
     *
     * @param factory RedisConnectionFactory
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        //创建 Jackson2JsonRedisSerializer 序列方式，对象类型使用 Object 类型，
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //设置一下 jackJson 的 ObjectMapper 对象参数
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // key 序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value 序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash key 序列化规则
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash value 序列化规则
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //属性设置后操作
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
