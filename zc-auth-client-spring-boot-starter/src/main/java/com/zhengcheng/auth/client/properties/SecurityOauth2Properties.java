package com.zhengcheng.auth.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全授权自定义配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/19 21:06
 */
@ConfigurationProperties(
        prefix = "security.oauth2"
)
public class SecurityOauth2Properties {

    private String resourceId;

}
