package com.zhengcheng.mq.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * SubscriptionTable
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Data
public class SubscriptionTable implements Serializable {
    /**
     * 主题
     */
    private String topic;
    /**
     * 消息过滤表达式
     */
    private String expression;
}
