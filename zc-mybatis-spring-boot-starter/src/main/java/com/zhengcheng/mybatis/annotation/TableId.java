package com.zhengcheng.mybatis.annotation;


import com.zhengcheng.mybatis.enums.IdType;

import java.lang.annotation.*;

/**
 * 表主键标识
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableId {

    /**
     * 字段值（驼峰命名方式，该值可无）
     */
    String value() default "";

    /**
     * 主键ID
     * {@link IdType}
     */
    IdType type() default IdType.AUTO;
}
