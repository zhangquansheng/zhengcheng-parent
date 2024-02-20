package com.zhengcheng.rocket.mq.annotation;

import com.aliyun.openservices.ons.api.ExpressionType;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.lang.annotation.*;

/**
 * 借鉴 @KafkaListener 实现自定义的 @RocketMQListener
 *
 * @author quansheng1.zhang
 * @since 2021/2/2 17:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RocketMQListener {

    String GROUP_ID_PLACEHOLDER = "${rocketmq.group-id:}";
    String TOPIC_PLACEHOLDER = "${rocketmq.topic:}";
    String NAME_SRV_ADDR_PLACEHOLDER = "${rocketmq.name-srv-addr:http://onsaddr.cn-hangzhou.mq-internal.aliyuncs.com:8080}";

    /**
     * nameSrvAddr
     */
    String nameSrvAddr() default NAME_SRV_ADDR_PLACEHOLDER;

    /**
     * Consumers of the same role is required to have exactly same subscriptions and consumerGroup to correctly achieve
     * load balance. It's required and needs to be globally unique.
     * <p>
     * <p>
     * See <a href="http://rocketmq.apache.org/docs/core-concept/">here</a> for further discussion.
     */
    String groupId() default GROUP_ID_PLACEHOLDER;

    /**
     * Topic name.
     */
    String topic() default TOPIC_PLACEHOLDER;

    /**
     * TAG or SQL92
     * <br>if null, equals to TAG
     *
     * @see ExpressionType#TAG
     * @see ExpressionType#SQL92
     */
    ExpressionType type() default ExpressionType.TAG;

    /**
     * Control which message can be select. Grammar please see {@link ExpressionType#TAG} and {@link ExpressionType#SQL92}
     */
    String expression() default "*";

    /**
     * Control message mode, if you want all subscribers receive message all message, broadcasting is a good choice.
     */
    MessageModel messageModel() default MessageModel.CLUSTERING;

    /**
     * 消费线程数量
     */
    int consumeThreadNums() default 20;
}
