package com.zhengcheng.tencentcloud;

import com.zhengcheng.tencentcloud.nlp.service.ITencentcloudNlpService;
import com.zhengcheng.tencentcloud.nlp.service.impl.TencentcloudNlpServiceImpl;
import com.zhengcheng.tencentcloud.properties.TencentcloudProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云自然语言自动配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 15:33
 */
@Configuration
@EnableConfigurationProperties({TencentcloudProperties.class})
public class NlpAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ITencentcloudNlpService tencentcloudNlpService(TencentcloudProperties tencentcloudProperties) {
        return new TencentcloudNlpServiceImpl(tencentcloudProperties);
    }
}
