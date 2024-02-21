package com.zhengcheng.cache.j2cache;

import com.github.benmanes.caffeine.cache.Cache;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;

import static com.zhengcheng.cache.j2cache.J2CacheConstants.J2CACHE_CHANNEL;


/**
 * J2Cache 消息订阅指定的 channel
 *
 * @author quansheng1.zhang
 * @since 2023/11/6 16:43
 */
@Slf4j
public class J2CacheMessageListener implements MessageListener {

    private final Cache<String, String> caffeine;

    public J2CacheMessageListener(Cache<String, String> caffeine) {
        this.caffeine = caffeine;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(pattern);
        String key = new String(message.getBody());
        log.info("Sec2CacheMessageListener key message:[{}] from channel: [{}]", key, channel);

        if (CharSequenceUtil.isNotBlank(key) && CharSequenceUtil.equalsIgnoreCase(J2CACHE_CHANNEL, channel)) {
            caffeine.invalidate(key);
        }
    }
}
