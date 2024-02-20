package com.zhengcheng.encrypt;

import com.zhengcheng.encrypt.advice.DecryptedRequestBodyAdvice;
import com.zhengcheng.encrypt.advice.EncryptedResponseBodyAdvice;
import com.zhengcheng.encrypt.properties.EncryptBodyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(EncryptBodyProperties.class)
@Import({EncryptedResponseBodyAdvice.class, DecryptedRequestBodyAdvice.class})
public class EncryptedAutoConfiguration {

    public EncryptedAutoConfiguration(EncryptBodyProperties encryptBodyProperties) {
        log.info("------ @Encrypted 自动配置  ---------------------------------------");
    }

}
