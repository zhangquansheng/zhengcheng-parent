package com.zhengcheng.mvc.interceptor;

import com.zhengcheng.common.constant.CommonConstant;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.utils.RequestUtil;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.mvc.annotation.RateLimiter;
import com.zhengcheng.mvc.enumeration.RateLimiterType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
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
@Slf4j
@Aspect
@Component
public class RateLimiterInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Around("execution(public * *(..)) && @annotation(com.zhengcheng.mvc.annotation.RateLimiter)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RateLimiter rateLimiterAnnotation = method.getAnnotation(RateLimiter.class);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = RequestUtil.getRemoteAddr(request);
        String key = rateLimiterAnnotation.key().concat(":");
        if (rateLimiterAnnotation.type() == RateLimiterType.CURRENT_USER) {
            key = key.concat(request.getParameter(CommonConstant.CURRENT_USER));
        } else {
            key = key.concat(ip);
        }
        long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisTemplate.expire(key, rateLimiterAnnotation.expire(), TimeUnit.MILLISECONDS);
        }
        if (count > rateLimiterAnnotation.limit()) {
            return Result.errorMessage("您的操作过于频繁，请稍后重试！");
        }
        String downKey = rateLimiterAnnotation.key().concat(":").concat("downline");
        String value = redisTemplate.opsForValue().get(downKey);
        long downCount = 0;
        if (Strings.isNotBlank(value)) {
            downCount = Long.valueOf(value);
        }
        if (downCount >= rateLimiterAnnotation.downLine()) {
            return Result.errorMessage("系统频繁，请稍后重试！");
        }
        long beginTime = System.currentTimeMillis();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Object retObj;
        try {
            retObj = pjp.proceed();
        } catch (Throwable e) {
            if (e instanceof BizException) {
                log.info("{}#{}#{}请求异常，错误：{}", className, methodName, pjp.getArgs(), e.getMessage());
                retObj = Result.errorMessage(e.getMessage());
            } else {
                log.error("{}#{}#{}请求异常，错误：{}", className, methodName, pjp.getArgs(), e.getMessage(), e);
                downCount = redisTemplate.opsForValue().increment(key, 1);
                if (downCount == 1) {
                    redisTemplate.expire(downKey, rateLimiterAnnotation.downtime(), TimeUnit.SECONDS);
                }
                retObj = Result.errorData("服务异常，请联系技术人员", Lang.getStackTrace(e));
            }
        }
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("{}#{}请求结束，耗时：{}ms", className, methodName, costMs);
        return retObj;
    }
}
