package com.zhengcheng.cat.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Cat自定义埋点注解
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 23:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CatTransaction {

    String type() default "Handler";

    String name() default "";
}
