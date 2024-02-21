package com.zhengcheng.cache.j2cache;

import com.github.benmanes.caffeine.cache.Cache;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * J2CacheRedisKeyExpirationListener
 *
 * @author quansheng1.zhang
 * @since 2023/11/6 17:54
 */
@Slf4j
public class J2CacheRedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private Cache<String, String> caffeine;

    public J2CacheRedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    // spring.redis.listener.channels=__keyevent@*:expired 默认监听所有的过期key，且是广播通知
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String expiredKey = message.toString();
            if (CharSequenceUtil.isNotBlank(expiredKey)) {
                if (log.isDebugEnabled()) {
                    log.debug("key message: " + expiredKey + " expired.");
                }
                caffeine.invalidate(expiredKey);
            }
        } catch (Exception ignored) {

        }
    }
}
