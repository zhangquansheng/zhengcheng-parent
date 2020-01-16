package com.zhengcheng.green;

import com.zhengcheng.green.properties.AcsProperties;
import com.zhengcheng.green.service.IAliYunGreenService;
import com.zhengcheng.green.service.impl.AliYunGreenServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云内容安全
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 23:01
 */
@Configuration
@EnableConfigurationProperties({AcsProperties.class})
@ConditionalOnProperty(
        prefix = "aliyun.acs",
        name = "access-key-id"
)
public class AliYunGreenAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IAliYunGreenService aliYunSmsService(AcsProperties acsProperties) {
        return new AliYunGreenServiceImpl(acsProperties);
    }

}
