package com.zhengcheng.rsa.encrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.zhengcheng.rsa.encrypt.advice.EncryptedRequestBodyAdvice;
import com.zhengcheng.rsa.encrypt.advice.EncryptedResponseBodyAdvice;
import com.zhengcheng.rsa.encrypt.properties.RsaEncryptProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@EnableConfigurationProperties(RsaEncryptProperties.class)
@ConditionalOnProperty(prefix = "rsa.encrypt", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({EncryptedResponseBodyAdvice.class, EncryptedRequestBodyAdvice.class})
public class EncryptedAutoConfiguration {

    public static final String RSA_BEAN_NAME = "rsa";

    private RsaEncryptProperties rsaEncryptProperties;

    public EncryptedAutoConfiguration(RsaEncryptProperties rsaEncryptProperties) {
        log.info("------ @Encrypted 自动配置  ---------------------------------------");
        this.rsaEncryptProperties = rsaEncryptProperties;
    }

    @Bean(RSA_BEAN_NAME)
    public RSA rsa() {
        return SecureUtil.rsa(Base64.decodeStr(rsaEncryptProperties.getPrivateKey()),
                Base64.decodeStr(rsaEncryptProperties.getPublicKey()));
    }

}
