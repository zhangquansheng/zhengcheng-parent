package com.zhengcheng.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * CacheManagerProperties
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/12 22:41
 */
@Data
@ConfigurationProperties(prefix = "zc.cache.manager")
public class CacheManagerProperties {

    private List<CacheConfig> configs;

    @Data
    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
