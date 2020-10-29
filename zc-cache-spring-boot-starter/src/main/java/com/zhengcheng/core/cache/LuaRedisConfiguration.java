package com.zhengcheng.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Lua脚本配置
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:05
 */
@Slf4j
@Configuration
public class LuaRedisConfiguration {

    @Bean
    public DefaultRedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/request_limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
