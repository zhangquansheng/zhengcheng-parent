package com.zhengcheng.aliyun.mq.annotation;

import java.lang.annotation.*;

/**
 * 事件监听 - 方法
 *
 * @author :    zhangquansheng
 * @date :    2020/4/15 16:09
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RocketmqListener {

    String[] tags() default {};
}
