package com.zhengcheng.web.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mvc自定义配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/19 21:06
 */
@Data
@ConfigurationProperties(
        prefix = "spring.mvc.custom"
)
public class CustomMvcProperties {

    private String mobileMaskType = "none";

}
