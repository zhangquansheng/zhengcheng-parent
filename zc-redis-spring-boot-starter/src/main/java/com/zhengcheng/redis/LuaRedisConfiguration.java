package com.zhengcheng.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zhengcheng.redis.annotation.RequestLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Lua脚本配置
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:05
 */
@Slf4j
@Configuration
@Import({SpringUtil.class})
@ConditionalOnClass(SpringUtil.class)
public class LuaRedisConfiguration {

    public LuaRedisConfiguration() {
        // 验证RequestLimit注解是否配置正确
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        if (Objects.nonNull(applicationContext)) {
            Set<String> requestLimitSet = new HashSet<>();
            String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                Object bean = applicationContext.getBean(beanDefinitionName);
                Method[] methods = bean.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    RequestLimit requestLimit = AnnotationUtils.findAnnotation(method, RequestLimit.class);
                    if (Objects.isNull(requestLimit)) {
                        continue;
                    }

                    String name = requestLimit.name();
                    if (StrUtil.isBlank(name)) {
                        continue;
                    }
                    if (requestLimitSet.contains(name)) {
                        throw new RuntimeException("RequestLimit[" + name + "] naming conflicts.");
                    } else {
                        requestLimitSet.add(name);
                        log.info("Generating unique request-limit operation named: {}", name);
                    }
                }
            }
        }
    }

    @Bean
    public DefaultRedisScript<Boolean> redisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/request_limit.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

}
