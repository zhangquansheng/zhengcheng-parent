package com.zhengcheng.aliyun;

import com.zhengcheng.aliyun.green.service.IAliYunGreenService;
import com.zhengcheng.aliyun.green.service.impl.AliYunGreenServiceImpl;
import com.zhengcheng.aliyun.properties.AliyunProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云内容安全自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/10/3 13:40
 */
@Configuration
@EnableConfigurationProperties({AliyunProperties.class})
@ConditionalOnProperty(
        prefix = "aliyun",
        name = "region-id"
)
public class GreenAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IAliYunGreenService aliYunGreenService(AliyunProperties aliyunProperties) {
        return new AliYunGreenServiceImpl(aliyunProperties);
    }
}
