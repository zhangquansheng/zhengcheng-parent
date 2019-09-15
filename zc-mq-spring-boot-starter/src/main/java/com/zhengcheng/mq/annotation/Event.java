package com.zhengcheng.mq.annotation;


import java.lang.annotation.*;

/**
 * 事件
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/12 22:41
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Event {
    String[] value();
}
