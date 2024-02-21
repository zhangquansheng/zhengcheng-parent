package com.zhengcheng.cache.j2cache.aspect;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.zhengcheng.cache.j2cache.CacheChannel;
import com.zhengcheng.cache.j2cache.DefaultCacheKeyBuilder;
import com.zhengcheng.cache.j2cache.annotation.Cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 缓存注解拦截（仅对返回值存在时，进行缓存）
 *
 * @author quansheng1.zhang
 * @since 2023/11/1 12:02
 */
@Aspect
@Slf4j
public class CacheAspect {

    private final CacheChannel cacheChannel;

    private final DefaultCacheKeyBuilder keyBuilder;

    private final ObjectMapper objectMapper;


    public CacheAspect(CacheChannel cacheChannel, DefaultCacheKeyBuilder keyBuilder, ObjectMapper objectMapper) {
        this.cacheChannel = cacheChannel;
        this.keyBuilder = keyBuilder;
        this.objectMapper = objectMapper;
    }

    @Pointcut("@annotation(com.zhengcheng.cache.j2cache.annotation.Cache)")
    public void aspect() {
    }

    @Around("aspect()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint invocation, Cache anno) throws Throwable {
        MethodSignature signature = (MethodSignature) invocation.getSignature();
        Method method = signature.getMethod();
        Object[] arguments = invocation.getArgs();
        String key = anno.name() + keyBuilder.buildKey(method, arguments, anno.keys());
        if (log.isDebugEnabled()) {
            log.debug("redis get key : {}", key);
        }
        String value = cacheChannel.get(key);
        if (CharSequenceUtil.isNotBlank(value)) {
            // 获取返回类型（带泛型）
            JavaType returnType = TypeFactory.defaultInstance().constructType(method.getGenericReturnType());
            try {
                return objectMapper.readValue(value, returnType);
            } catch (IOException e) {
                log.error("CacheAspect objectMapper readValue Exception {}", e.getMessage(), e);
            }
        }

        Object result = invocation.proceed();
        if (CharSequenceUtil.isNotBlank(key) && Objects.nonNull(result)) {
            String resultJson = JSON.toJSONString(result);
            if ("[]".equalsIgnoreCase(resultJson) || CharSequenceUtil.isBlank(resultJson)) {
                if (log.isDebugEnabled()) {
                    log.debug("resultJson is empty or blank : {}", resultJson);
                }
            } else {
                if (anno.expire() == -1) {
                    cacheChannel.set(key, resultJson);
                } else {
                    cacheChannel.set(key, resultJson, anno.expire(), anno.unit());
                }

                if (log.isDebugEnabled()) {
                    log.debug("redis set key : {} , value : {}", key, resultJson);
                }
            }
        }
        return result;
    }


}
