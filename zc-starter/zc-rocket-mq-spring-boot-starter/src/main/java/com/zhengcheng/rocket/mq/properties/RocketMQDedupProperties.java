package com.zhengcheng.rocket.mq.properties;

import com.zhengcheng.common.constant.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.time.Duration;

/**
 * RocketMQDedupProperties
 *
 * @author quansheng1.zhang
 * @since 2020/12/8 20:42
 */
@RefreshScope
@Data
@ConfigurationProperties(CommonConstants.ROCKETMQ_DEDUP_PREFIX)
public class RocketMQDedupProperties {

    /**
     * 对于消费中的消息，多少毫秒内认为重复，默认一分钟，即一分钟内的重复消息都会串行处理（等待前一个消息消费成功/失败），超过这个时间如果消息还在消费就不认为重复了（为了防止消息丢失）
     */
    private Duration processingExpire = Duration.ofMillis(60 * 1000);

    /**
     * 消息消费成功后，记录保留多少分钟，默认一天，即一天内的消息不会重复
     */
    private Duration recordReserve = Duration.ofMinutes(60 * 24);
}
