package com.zhengcheng.mvc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * mvc自定义配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/28 10:42
 */
@Data
@RefreshScope
@ConfigurationProperties(
        prefix = "spring.mvc.custom"
)
public class CustomMvcProperties {

    private String aesKey = "xinzhengcheng123";

}
