package com.zhengcheng.cache.j2cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 缓存注解
 *
 * @author quansheng1.zhang
 * @since 2023/11/1 11:51
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cache {
    /**
     * 缓存名称
     *
     * @return 名称(前缀)
     */
    String name() default "";

    /**
     * support SPEL expresion 缓存的key = keys
     *
     * @return
     */
    String[] keys() default {""};

    /**
     * 缓存的过期时间，单位：秒，如果为-1则表示永久缓存
     *
     * @return
     */
    long expire() default -1L;

    /**
     * unit must not be {@literal null}.
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
