package cn.seczone.halo.ratelimit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import cn.seczone.halo.ratelimit.aspect.RateLimiterAspect;
import cn.seczone.halo.ratelimit.handler.DefaultLimitKeyHandler;
import cn.seczone.halo.ratelimit.handler.LimitKeyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * redis 配置类
 * https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#redis
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/12 22:41
 */
@Slf4j
public class RateLimiterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = LimitKeyHandler.class)
    public LimitKeyHandler stringRandomGenerator() {
        return new DefaultLimitKeyHandler();
    }

    @Bean
    public DefaultRedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/rate_limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean
    @ConditionalOnBean({LimitKeyHandler.class, DefaultRedisScript.class, StringRedisTemplate.class})
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect();
    }

}
