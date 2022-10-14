package com.zhengcheng.cache.ratelimit.aspect;

import ch.qos.logback.core.CoreConstants;
import com.zhengcheng.cache.ratelimit.annotation.RateLimiter;
import com.zhengcheng.cache.ratelimit.enums.LimitType;
import com.zhengcheng.cache.ratelimit.handler.LimitKeyHandler;
import com.zhengcheng.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * 访问接口限流
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:15
 */
@Slf4j
@Aspect
@Component
@ConditionalOnClass({DefaultRedisScript.class, StringRedisTemplate.class})
@DependsOn("redisScript")
public class RateLimiterAspect {

    private static final String REDIS_LIMIT_KEY_PREFIX = "limit:";

    @Autowired
    private DefaultRedisScript<Boolean> redisScript;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private LimitKeyHandler limitKeyHandler;

    @Pointcut("@annotation(com.zhengcheng.cache.ratelimit.annotation.RateLimiter)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            // 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
            Signature signature = joinPoint.getSignature();
            //拦截的方法名称
            String methodName = signature.getName();
            //拦截的放参数类型
            Class[] parameterTypes = ((MethodSignature) signature).getMethod().getParameterTypes();
            Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            // 必须要用AnnotationUtils，才能获取到 name 和 value上@AliasFor(互为别名)的作用
            // AOP原理
            RateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, RateLimiter.class);
            if (Objects.isNull(rateLimiter)) {
                return;
            }

            String limitKey = limitKey(method, rateLimiter.limitType(), Arrays.hashCode(joinPoint.getArgs()));
            if (log.isDebugEnabled()) {
                log.debug("限流接口的KEY:[{}]", limitKey);
            }

            Boolean allow = stringRedisTemplate.execute(redisScript, Collections.singletonList(limitKey), String.valueOf(rateLimiter.count()), //limit
                    String.valueOf(rateLimiter.timeUnit().toMillis(rateLimiter.time()))); //expire

            if (Objects.equals(Boolean.FALSE, allow)) {
                throw new BizException(rateLimiter.message());
            }
        } catch (NoSuchMethodException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    /**
     * 获取限流key => limit:ip.clazzName.methodName || limit:userName.clazzName.methodName
     *
     * @param method       Method
     * @param limitTypes   LimitTypes
     * @param argsHashCode argsHashCode
     * @return String
     */
    private String limitKey(Method method, LimitType[] limitTypes, int argsHashCode) {

        StringBuilder limitKey = new StringBuilder(REDIS_LIMIT_KEY_PREFIX);
        for (LimitType limitType : limitTypes) {
            switch (limitType) {
                case ARGS:
                    limitKey.append(argsHashCode);
                    break;
                case IP:
                    limitKey.append(limitKeyHandler.getIpKey());
                    break;
                case USER:
                    limitKey.append(limitKeyHandler.getUserKey());
                    break;
                case METHOD:
                    limitKey.append(method.getDeclaringClass().getName()).append(CoreConstants.DOT).append(method.getName());
                    break;
                default:
                    // default METHOD
                    break;
            }
            limitKey.append(CoreConstants.DOT);
        }
        return limitKey.toString();
    }
}
