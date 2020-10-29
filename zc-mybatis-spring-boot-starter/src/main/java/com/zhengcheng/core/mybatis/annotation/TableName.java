package com.zhengcheng.core.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 数据库表相关
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableName {

    /**
     * 实体对应的表名
     */
    String value() default "";
}
