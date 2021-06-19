package com.zhengcheng.rocket.mq.annotation;

import java.lang.annotation.*;

/**
 * RocketMQ 消息去重，仅支持正常消息消费（MessageListener）、顺序消息消费（MessageOrderListener）
 *
 * @author quansheng1.zhang
 * @since 2020/12/8 18:01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMQDedup {
}
