package com.zhengcheng.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "caffeine")
public class CaffeineCacheAutoConfiguration {

    public CaffeineCacheAutoConfiguration() {
        log.info("------ 内存级缓存 Springboot2.x 使用 Caffeine ----------------------------------");
    }

    @Value("${spring.cache.caffeine.spec}")
    private String cacheSpecification;

    @Bean
    public Cache<String, String> caffeineCache() {
        Caffeine<Object, Object> cacheBuilder = Caffeine.from(cacheSpecification);
        return cacheBuilder.build();
    }

}
