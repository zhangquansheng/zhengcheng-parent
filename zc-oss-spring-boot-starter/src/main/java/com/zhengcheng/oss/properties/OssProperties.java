package com.zhengcheng.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云OSS属性
 *
 * @author :    quansheng.zhang
 * @date :    2019/10/3 13:40
 */
@Data
@ConfigurationProperties(prefix = "zc.oss")
public class OssProperties {
    /**
     * 密钥key
     */
    private String accessKey;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
    /**
     * 端点
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 说明
     */
    private String domain;
}
