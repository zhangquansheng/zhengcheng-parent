package com.zhengcheng.xxl.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * XxlJobProperties
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/5 19:05
 */
@Data
@ConfigurationProperties(prefix = "xxl.job.executor")
public class XxlJobExecutorProperties {

    private String adminAddresses;

    private String appName;

    private String ip = "";

    private int port = 9999;

    private String accessToken = "";

    private String logPath;

    private int logRetentionDays = -1;
}
