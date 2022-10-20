package com.zhengcheng.rsa.encrypt.properties;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Rsa 加密属性
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 10:17
 */
@ConfigurationProperties(prefix = "encrypt.body")
@Configuration
@Data
public class EncryptBodyProperties {

    private RsaProperties ras;

    private AesProperties aes;

    @Data
    public static class RsaProperties {
        /**
         * RSA加密私钥，BASE64 加密
         */
        private String privateKey;
        /**
         * RSA加密公钥，BASE64 加密
         */
        private String publicKey;
    }

    @Data
    public static class AesProperties {

        private Mode mode;

        private Padding padding;

        private String key;
    }

}
