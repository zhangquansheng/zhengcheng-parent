package com.zhengcheng.rsa.encrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.zhengcheng.rsa.encrypt.advice.EncryptedResponseBodyAdvice;
import com.zhengcheng.rsa.encrypt.properties.EncryptProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

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

    public static final String AES_BEAN_NAME = "aesEncrypted";


    private EncryptProperties encryptProperties;

    public EncryptedAutoConfiguration(EncryptProperties encryptProperties) {
        log.info("------ @Encrypted 自动配置  ---------------------------------------");
        this.encryptProperties = encryptProperties;
    }

    @Bean(RSA_BEAN_NAME)
    public RSA rsa() {
        return SecureUtil.rsa(Base64.decodeStr(encryptProperties.getRas().getPrivateKey()), Base64.decodeStr(encryptProperties.getRas().getPublicKey()));
    }

    @Bean(AES_BEAN_NAME)
    public AES aes() {
        return new AES(Objects.isNull(encryptProperties.getAes().getMode()) ? Mode.ECB : encryptProperties.getAes().getMode(), Objects.isNull(encryptProperties.getAes().getPadding()) ? Padding.PKCS5Padding : encryptProperties.getAes().getPadding(), new SecretKeySpec(encryptProperties.getAes().getKey().getBytes(), "AES"));
    }

}
