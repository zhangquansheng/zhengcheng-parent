package com.zhengcheng.swagger.mq.properties;

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

    private String id;
    private String accessKey;
    private String secretKey;
    private long sendTimeout = 3000L;
}
