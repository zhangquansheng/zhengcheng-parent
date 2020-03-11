package com.zhengcheng.aliyun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云主账号配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 9:18
 */
@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliYunProperties {
    /**
     * 密钥key
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String accessKeyId;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
}
