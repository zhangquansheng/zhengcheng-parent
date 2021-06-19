package com.zhengcheng.rsa.encrypt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Rsa 加密属性
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 10:17
 */
@ConfigurationProperties(prefix = "rsa.encrypt")
@Configuration
@Data
public class RsaEncryptProperties {

    /**
     * RSA加密私钥，BASE64 加密
     */
    private String privateKey;
    /**
     * RSA加密公钥，BASE64 加密
     */
    private String publicKey;
    /**
     * 是否启用
     */
    private boolean enabled = true;

}
