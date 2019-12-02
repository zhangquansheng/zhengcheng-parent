package com.zhengcheng.green.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云内容安全属性配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 22:42
 */
@Data
@ConfigurationProperties(prefix = "aliyun.acs")
public class AcsProperties {
    /**
     * 区域ID
     */
    private String regionId;
    /**
     * AccessKeyId
     */
    private String accessKeyId;
    /**
     * AccessKeySecret
     */
    private String accessKeySecret;
    /**
     * 超时时间, 服务端全链路处理超时时间为10秒
     */
    private Integer connectTimeout = 3000;
    /**
     * ReadTimeout小于服务端处理的时间，程序中会获得一个read timeout异常
     */
    private Integer readTimeout = 10000;
}
