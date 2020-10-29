package com.zhengcheng.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内存级缓存 Springboot2.x 使用 Caffeine
 * https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-caching
 *
 * @author :    zhangquansheng
 * @date :    2020/5/22 16:07
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "caffeine")
public class CaffeineCacheAutoConfiguration {

    @Value("${spring.cache.caffeine.spec}")
    private String cacheSpecification;

    @Bean
    public Cache<String, String> caffeineCache() {
        Caffeine<Object, Object> cacheBuilder = Caffeine.from(cacheSpecification);
        return cacheBuilder.build();
    }

}
