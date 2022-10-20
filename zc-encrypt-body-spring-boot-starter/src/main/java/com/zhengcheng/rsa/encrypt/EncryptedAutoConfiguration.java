package com.zhengcheng.rsa.encrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.zhengcheng.rsa.encrypt.advice.EncryptedResponseBodyAdvice;
import com.zhengcheng.rsa.encrypt.properties.EncryptProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link com.zhangmen.brain.solar.rsa.encrypt.annotation.Encrypted} 注解对请求入参解密，对返回参加密
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 14:08
 */
@SuppressWarnings("ALL")
@Slf4j
@Configuration
@EnableConfigurationProperties(EncryptProperties.class)
@Import({EncryptedResponseBodyAdvice.class})
public class EncryptedAutoConfiguration {

    public static final String RSA_BEAN_NAME = "rsaEncrypted";

    private EncryptProperties encryptProperties;

    public EncryptedAutoConfiguration(EncryptProperties encryptProperties) {
        log.info("------ @Encrypted 自动配置  ---------------------------------------");
        this.encryptProperties = encryptProperties;
    }

    @Bean(RSA_BEAN_NAME)
    public RSA rsa() {
        return SecureUtil.rsa(Base64.decodeStr(encryptProperties.getRas().getPrivateKey()), Base64.decodeStr(encryptProperties.getRas().getPublicKey()));
    }

}
