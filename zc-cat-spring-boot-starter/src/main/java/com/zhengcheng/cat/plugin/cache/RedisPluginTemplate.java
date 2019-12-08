package com.zhengcheng.cat.plugin.cache;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.AbstractPluginTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.lang.reflect.Method;

/**
 * RedisPluginTemplate
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/8 1:01
 */
@Slf4j
@Aspect
@ConditionalOnClass({RedisProperties.class})
public class RedisPluginTemplate extends AbstractPluginTemplate {

    @Pointcut("execution(public * org.springframework.data.redis.core.RedisTemplate.*(..))")
    public void redisMethodPointcut() {
    }

    @Override
    @Around("redisMethodPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return super.doAround(pjp);
    }

    @Override
    protected Transaction beginLog(ProceedingJoinPoint pjp) {
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        return Cat.newTransaction("Cache.Redis", method.getName());
    }

    @Override
    protected void endLog(Transaction transaction, Object retVal, Object... params) {

    }

}
