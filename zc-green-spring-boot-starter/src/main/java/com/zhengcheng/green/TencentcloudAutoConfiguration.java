package com.zhengcheng.green;

import com.zhengcheng.green.properties.TencentcloudProperties;
import com.zhengcheng.green.service.ITencentcloudCmsService;
import com.zhengcheng.green.service.impl.TencentcloudCmsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云内容安全
 *
 * @author :    zhangquansheng
 * @date :    2020/1/16 17:54
 */
@Configuration
@EnableConfigurationProperties({TencentcloudProperties.class})
public class TencentcloudAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ITencentcloudCmsService tencentcloudCmsService(TencentcloudProperties tencentcloudProperties) {
        return new TencentcloudCmsServiceImpl(tencentcloudProperties);
    }

}
