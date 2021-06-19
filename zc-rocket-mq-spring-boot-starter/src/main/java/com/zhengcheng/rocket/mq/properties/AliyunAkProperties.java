package com.zhengcheng.rocket.mq.properties;

import com.zhengcheng.common.constant.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * apollo 获取到 aliyun.ak，aliyun.sec
 *
 * @author quansheng1.zhang
 * @since 2021/1/9 13:34
 */
@Data
@ConfigurationProperties(CommonConstants.ALIYUN_AK_PREFIX)
public class AliyunAkProperties {
    /**
     * 是否启用AK
     */
    private Boolean akEnabled;
    /**
     * 对应aliyun.ak的后缀ak
     */
    private String ak;
    /**
     * 对应aliyun.sec的后缀sec
     */
    private String sec;
}
