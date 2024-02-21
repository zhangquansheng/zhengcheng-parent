package com.zhengcheng.ratelimit.annotation;


import com.zhengcheng.ratelimit.enums.LimitType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 限流器
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {

    /**
     * 允许访问的最大次数
     */
    int count() default 10;

    /**
     * 时间段，默认值 1 秒
     */
    long time() default 1000;

    /**
     * 时间段的时间单位，默认 秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 限流类型，可组合
     */
    LimitType[] limitType() default LimitType.METHOD;

    /**
     * 限流的keys，在 LimitType.ARGS 的情况下生效
     * spel表达式，支持多项拼接
     */
    String[] keys() default {};

    /**
     * 限流keys的分隔符，在 LimitType.ARGS 的情况下生效
     */
    String split() default "-";

    String message() default "RateLimitInvocationException";
}
