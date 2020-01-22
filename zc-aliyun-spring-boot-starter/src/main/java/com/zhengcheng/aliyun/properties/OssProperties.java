package com.zhengcheng.aliyun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云OSS属性
 *
 * @author :    quansheng.zhang
 * @date :    2019/10/3 13:40
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 域名
     */
    private String domain;
}
