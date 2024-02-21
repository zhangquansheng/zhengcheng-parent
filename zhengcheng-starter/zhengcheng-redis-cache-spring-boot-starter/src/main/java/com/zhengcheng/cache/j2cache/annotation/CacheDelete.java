package com.zhengcheng.cache.j2cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CacheDelete
 *
 * @author quansheng1.zhang
 * @since 2023/11/1 11:52
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheDelete {
    /**
     * 缓存名称
     *
     * @return 名称(前缀)
     */
    String name() default "";

    /**
     * support SPEL expresion 锁的key = name + keys
     *
     * @return
     */
    String[] keys() default {""};

}
