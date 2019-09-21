package com.zhengcheng.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mvc自定义配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/19 21:06
 */
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
