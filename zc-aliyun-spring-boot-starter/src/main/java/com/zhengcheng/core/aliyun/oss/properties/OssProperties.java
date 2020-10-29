package com.zhengcheng.core.aliyun.oss.properties;

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
     * 端点，Endpoint以杭州（http://oss-cn-hangzhou.aliyuncs.com）为例，其它Region请按实际情况填写
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * 域名
     */
    private String domain;
}
