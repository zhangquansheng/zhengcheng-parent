package com.zhengcheng.mq.properties;

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
     * AccessKeyId 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    private String accessKey;
    /**
     * AccessKeySecret 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    private String secretKey;
    /**
     * 发送消息超时时间
     */
    private long sendTimeout = 3000L;
}
