package com.zhengcheng.core.zk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zk curator 配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 9:18
 */
@Data
@ConfigurationProperties(prefix = "curator")
public class CuratorProperties {
    private int baseSleepTimeMs = 1000;
    private int maxRetries = 3;
    private String zookeeperConnectionString;
}
