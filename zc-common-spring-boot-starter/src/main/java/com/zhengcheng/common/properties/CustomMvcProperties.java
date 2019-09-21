package com.zhengcheng.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * mvc自定义配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/19 21:06
 */
@RefreshScope
@ConfigurationProperties(
        prefix = "spring.mvc.custom"
)
public class CustomMvcProperties {

    private String mobileMaskType = "none";

    public CustomMvcProperties() {
    }

    public String getMobileMaskType() {
        return this.mobileMaskType;
    }

    public void setMobileMaskType(String mobileMaskType) {
        this.mobileMaskType = mobileMaskType;
    }

}
