package com.zhengcheng.cache.idempotent.annotation;

import java.util.concurrent.TimeUnit;

/**
 * 幂等或防重复提交
 *
 * @author quansheng.zhang
 * @since 2022/8/16 15:16
 */
public @interface Idempotent {
    /**
     * 幂等的key，不设置则取所有参数toString
     * spel表达式，支持多项拼接
     */
    String[] keys() default {};

    /**
     * keys的分隔符
     */
    String split() default "-";

    /**
     * 锁过期时间
     */
    int timeout() default 5000;

    /**
     * 锁过期时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 锁的位置，不设置则取URI
     */
    String location() default "";

    /**
     * 提醒信息
     */
    String info() default "操作过于频繁，请稍后重试";

    /**
     * 执行完成后是否释放key
     */
    boolean delKey() default false;
}
