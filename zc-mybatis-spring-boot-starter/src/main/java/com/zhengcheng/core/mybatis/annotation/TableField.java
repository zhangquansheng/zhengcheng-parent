package com.zhengcheng.core.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 表字段标识
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {

    /**
     * 数据库字段值
     */
    String value() default "";

    /**
     * 是否为数据库表字段
     * 默认 true 存在，false 不存在
     */
    boolean exist() default true;

    /**
     * 是否进行 select 查询
     * <p>大字段可设置为 false 不加入 select 查询范围</p>
     */
    boolean select() default true;
}
