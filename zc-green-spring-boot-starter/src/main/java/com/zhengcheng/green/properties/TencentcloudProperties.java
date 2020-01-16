package com.zhengcheng.green.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯云属性
 *
 * @author :    zhangquansheng
 * @date :    2020/1/16 17:22
 */
@Data
@ConfigurationProperties(prefix = "tencentcloud")
public class TencentcloudProperties {

    /**
     * 密钥 https://console.cloud.tencent.com/cam/capi
     */
    private String secretId;

    /**
     * 密钥 https://console.cloud.tencent.com/cam/capi
     */
    private String secretKey;

    /**
     * 地域参数，用来标识希望操作哪个地域的数据。接口接受的地域取值参考接口文档中输入参数公共参数 Region 的说明。注意：某些接口不需要传递该参数，接口文档中会对此特别说明，此时即使传递该参数也不会生效。
     */
    private String region = "ap-guangzhou";
}
