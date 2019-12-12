package com.zhengcheng.web.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ExecutorProperties
 *
 * @author :    zhangquansheng
 * @date :    2019/12/12 12:53
 */
@Data
@ConfigurationProperties(
        prefix = "executor"
)
public class ExecutorProperties {

    private int corePoolSize = 10;

    private int maxPoolSize = 15;

    private int queueCapacity = 20;

    private int keepAliveSeconds = 60;
}
