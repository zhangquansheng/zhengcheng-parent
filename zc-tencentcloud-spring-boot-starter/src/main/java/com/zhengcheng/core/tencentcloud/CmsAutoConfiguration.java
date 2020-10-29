package com.zhengcheng.core.tencentcloud;

import com.zhengcheng.core.tencentcloud.cms.service.ITencentcloudCmsService;
import com.zhengcheng.core.tencentcloud.cms.service.impl.TencentcloudCmsServiceImpl;
import com.zhengcheng.core.tencentcloud.properties.TencentcloudProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云内容安全自动配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/16 17:54
 */
@Configuration
@EnableConfigurationProperties({TencentcloudProperties.class})
public class CmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ITencentcloudCmsService tencentcloudCmsService(TencentcloudProperties tencentcloudProperties) {
        return new TencentcloudCmsServiceImpl(tencentcloudProperties);
    }

}
