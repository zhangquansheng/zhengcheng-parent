package com.zhengcheng.aliyun.mq.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ProducerProperties
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Data
@ConfigurationProperties(prefix = "mq.producer")
public class ProducerProperties {
    /**
     * 您在控制台创建的 Group ID
     */
    private String id;
    /**
     * 发送消息超时时间
     */
    private long sendTimeout = 3000L;
}
