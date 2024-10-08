package com.zhengcheng.idempotent.aspectj;

import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.expression.KeyResolver;
import com.zhengcheng.idempotent.annotation.Idempotent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * IdempotentAspect
 *
 * @author quansheng.zhang
 * @since 2022/8/16 15:25
 */
@Slf4j
@Aspect
public class IdempotentAspect {

    private static final String REPEAT_LOCK_PREFIX = "sz:halo:idempotent:";

    private final RedissonClient redissonClient;
    private final KeyResolver keyResolver;

    public IdempotentAspect(RedissonClient redissonClient, KeyResolver keyResolver) {
        this.redissonClient = redissonClient;
        this.keyResolver = keyResolver;
    }

    @Pointcut("@annotation(com.zhengcheng.idempotent.annotation.Idempotent)")
    public void pointCut() {
    }

    /**
     * around
     */
    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return joinPoint.proceed(args);
        }

        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        String lockKey = getLockKey(idempotent, joinPoint);
        RLock lock = redissonClient.getLock(lockKey);
        if (lock == null || !lock.tryLock(0, idempotent.timeout(), idempotent.timeUnit())) {
            log.error("handle present repeat submission tryLock failed, lockKey: {}", lockKey);
            throw new BizException(idempotent.message());
        }

        try {
            return joinPoint.proceed(args);
        } finally {
            if (idempotent.delKey() && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private String getLockKey(Idempotent idempotent, ProceedingJoinPoint joinPoint) {
        String suffix;
        if ((idempotent.keys() == null || idempotent.keys().length == 0) && (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0)) {
            suffix = Arrays.asList(joinPoint.getArgs()).toString();
        } else {
            suffix = keyResolver.resolver(idempotent.keys(), idempotent.split(), joinPoint);
        }

        String location;
        if (!StringUtils.hasText(idempotent.location())) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = requestAttributes.getRequest();
            location = request.getRequestURI();
        } else {
            location = idempotent.location();
        }
        return REPEAT_LOCK_PREFIX + location + ":" + suffix;
    }

}
