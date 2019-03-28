package com.zhengcheng.mvc.annotation;

import com.zhengcheng.mvc.enumeration.RateLimiterType;

import java.lang.annotation.*;

/**
 * 限流
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.annotation
 * @Description :
 * @date :    2019/3/28 23:32
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 资源的key
     */
    String key() default "rate:limiter";

    /**
     * 单位时间限制通过请求总数,0表示不限制
     *
     * @return
     */
    int total() default 1000;

    /**
     * 单位时间限制通过请求数,0表示不限制
     */
    int limit() default 10;

    /**
     * 过期时间，单位毫秒
     */
    int expire() default 1000;

    /**
     * 类型
     */
    RateLimiterType type() default RateLimiterType.IP;
}
