package com.zhengcheng.xxl.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * apollo - AppProperties
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/5 18:56
 */
@Data
@ConfigurationProperties(prefix = "app")
@PropertySource(value = "classpath:/META-INF/app.properties", encoding = "UTF-8")
public class AppProperties {

    private String id;
    private String name;
}
