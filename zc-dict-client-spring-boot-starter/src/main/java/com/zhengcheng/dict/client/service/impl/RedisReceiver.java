package com.zhengcheng.dict.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Redis 消息接收者
 *
 * @author :    zhangquansheng
 * @date :    2020/5/25 14:37
 */
@Slf4j
@Component
public class RedisReceiver implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("channel:{},receive message body: {}", channel, body);
    }
}
