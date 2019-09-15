package com.zhengcheng.swagger.mq.properties;

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
    private String topic;
    private String expression;
}
