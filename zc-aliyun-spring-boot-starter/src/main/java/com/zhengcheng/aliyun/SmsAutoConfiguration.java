package com.zhengcheng.aliyun;

import com.zhengcheng.aliyun.properties.AliYunProperties;
import com.zhengcheng.aliyun.sms.properties.SmsProperties;
import com.zhengcheng.aliyun.sms.service.IAliYunSmsService;
import com.zhengcheng.aliyun.sms.service.impl.AliYunSmsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息服务自动配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 12:58
 */
@Configuration
@EnableConfigurationProperties({AliYunProperties.class, SmsProperties.class})
public class SmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IAliYunSmsService aliYunSmsService(AliYunProperties aliyunProperties, SmsProperties smsProperties) {
        return new AliYunSmsServiceImpl(aliyunProperties, smsProperties);
    }
}
