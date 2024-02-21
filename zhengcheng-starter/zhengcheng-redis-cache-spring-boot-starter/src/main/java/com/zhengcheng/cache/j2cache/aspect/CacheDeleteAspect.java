package com.zhengcheng.cache.j2cache.aspect;

import com.zhengcheng.cache.j2cache.CacheChannel;
import com.zhengcheng.cache.j2cache.DefaultCacheKeyBuilder;
import com.zhengcheng.cache.j2cache.annotation.CacheDelete;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * 清除缓存注解拦截
 *
 * @author quansheng1.zhang
 * @since 2023/11/1 14:48
 */
@Aspect
@Slf4j
public class CacheDeleteAspect {


    private final CacheChannel cacheChannel;
    private final DefaultCacheKeyBuilder keyBuilder;


    public CacheDeleteAspect(CacheChannel cacheChannel, DefaultCacheKeyBuilder defaultCacheKeyBuilder) {
        this.cacheChannel = cacheChannel;
        this.keyBuilder = defaultCacheKeyBuilder;
    }

    @Pointcut("@annotation(com.zhengcheng.cache.j2cache.annotation.CacheDelete)")
    public void aspect() {
    }

    @Around("aspect()&&@annotation(anno)")
    public Object interceptor(ProceedingJoinPoint invocation, CacheDelete anno) throws Throwable {
        Object result = invocation.proceed();
        try {
            MethodSignature signature = (MethodSignature) invocation.getSignature();
            Method method = signature.getMethod();
            Object[] arguments = invocation.getArgs();
            String key = anno.name() + keyBuilder.buildKey(method, arguments, anno.keys());
            cacheChannel.delete(key);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return result;
    }

}
