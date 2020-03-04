package com.zhengcheng.tencentcloud.cos.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * COS 属性
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/4 20:59
 */
@Data
@ConfigurationProperties(prefix = "tencentcloud.cos")
public class CosProperties {
    /**
     * bucket名称
     */
    private String bucketName;
}
