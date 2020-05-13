package com.zhengcheng.redis.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 请求限流
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequestLimit {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    /**
     * 允许访问的最大次数
     */
    int count() default Integer.MAX_VALUE;

    /**
     * 时间段，单位为毫秒，默认值1秒
     */
    long time() default 1000;
}
