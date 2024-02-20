package com.zhengcheng.rocket.mq;

import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.rocket.mq.properties.AliyunAkProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * AkAutoConfiguration
 *
 * @author quansheng1.zhang
 * @since 2021/1/9 13:34
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AkAutoConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = CommonConstants.ALIYUN_AK_PREFIX)
    public AliyunAkProperties aliyunAkProperties() {
        return new AliyunAkProperties();
    }
}
