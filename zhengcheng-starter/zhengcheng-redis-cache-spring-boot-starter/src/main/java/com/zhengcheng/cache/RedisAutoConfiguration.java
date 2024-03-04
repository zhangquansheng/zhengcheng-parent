package com.zhengcheng.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * redis 配置类
 * https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/12 22:41
 */
@Slf4j
@ComponentScan(basePackages = "com.zhengcheng.cache.redis")
@EnableCaching
public class RedisAutoConfiguration extends CachingConfigurerSupport {

    public RedisAutoConfiguration() {
        log.info("------ redis 缓存管理 自动配置  ----------------------------------");
        log.info("------ @EnableCaching 启动缓存管理 ------------------------------");
        log.info("------ Spring Redis 工具类 RedisCache --------------------------");
        log.info("---------------------------------------------------------------");
    }

    /**
     * RedisTemplate配置
     *
     * @param factory RedisConnectionFactory
     */
    @Primary
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

    @Primary
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
