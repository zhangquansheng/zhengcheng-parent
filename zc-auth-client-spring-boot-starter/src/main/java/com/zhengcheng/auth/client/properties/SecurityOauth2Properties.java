package com.zhengcheng.auth.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全授权自定义配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/19 21:06
 */
@ConfigurationProperties(
        prefix = "security.oauth2"
)
@Data
public class SecurityOauth2Properties {

    private String resourceId;

    private List<String> permitAll = new ArrayList<>();
}
