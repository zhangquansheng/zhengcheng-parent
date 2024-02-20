package com.zhengcheng.rocket.mq.properties;

import lombok.Data;

/**
 * apollo 获取到 aliyun.ak，aliyun.sec
 *
 * @author quansheng1.zhang
 * @since 2021/1/9 13:34
 */
@Data
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
