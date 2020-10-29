package com.zhengcheng.mybatis.plus.annotation;


import java.lang.annotation.*;

/**
 * Connection中的readonly属性设置成TRUE
 *
 * @author :    quansheng.zhang
 * @date :    2020/01/03 11:18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReadOnlyConnection {

}
