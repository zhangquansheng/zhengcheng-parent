package com.zhengcheng.mvc.interceptor;

import com.zhengcheng.common.web.Result;
import com.zhengcheng.mvc.annotation.RateLimiter;
import com.zhengcheng.mvc.enumeration.RateLimiterType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 限流拦截器
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.interceptor
 * @Description :
 * @date :    2019/3/28 23:41
 */
@Aspect
@Configuration
public class RateLimiterInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Around("execution(public * *(..)) && @annotation(com.zhengcheng.mvc.annotation.RateLimiter)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RateLimiter rateLimiterAnnotation = method.getAnnotation(RateLimiter.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        String key = rateLimiterAnnotation.key().concat(":");
        if (rateLimiterAnnotation.type() == RateLimiterType.CURRENT_USER) {
            String currentUserId = request.getParameter("currentUserId");
            key = key.concat(currentUserId);
        } else {
            key = key.concat(ip);
        }
        Integer total = redisTemplate.keys(rateLimiterAnnotation.key().concat("*")).size();
        if (rateLimiterAnnotation.total() != 0 && total > rateLimiterAnnotation.total()) {
            return Result.errorMessage("系统繁忙");
        }
        long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisTemplate.expire(key, rateLimiterAnnotation.expire(), TimeUnit.MILLISECONDS);
        }
        if (rateLimiterAnnotation.limit() != 0 && count > rateLimiterAnnotation.limit()) {
            return Result.errorMessage("访问频繁");
        }
        return pjp.proceed();
    }
}
