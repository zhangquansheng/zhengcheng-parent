package com.zhengcheng.core.aliyun.sms.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云短信SMS属性
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/14 17:02
 */
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class SmsProperties {
    /**
     * 区域-默认值cn-hangzhou
     */
    private String regionId;
}
