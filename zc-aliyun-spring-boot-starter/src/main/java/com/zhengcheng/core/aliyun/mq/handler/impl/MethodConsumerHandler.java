package com.zhengcheng.core.aliyun.mq.handler.impl;

import com.aliyun.openservices.ons.api.Action;
import com.zhengcheng.core.aliyun.mq.handler.IConsumerHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 方法 消费者处理
 *
 * @author :    zhangquansheng
 * @date :    2020/4/15 16:31
 */
@Slf4j
public class MethodConsumerHandler implements IConsumerHandler {

    private final Object target;
    private final Method method;

    public MethodConsumerHandler(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Action execute(String body) {
        try {
            return (Action) method.invoke(target, new Object[]{body});
        } catch (Exception e) {
            log.error("method:[{}],body:[{}],execute exception:[{}]", method, body, e.getMessage(), e);
            return Action.ReconsumeLater;
        }
    }
}
