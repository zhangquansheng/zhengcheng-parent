package com.zhengcheng.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.zhengcheng.oss.properties.OssProperties;
import com.zhengcheng.oss.service.IAliYunOssService;
import com.zhengcheng.oss.service.impl.AliYunOssServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/10/3 13:40
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({OssProperties.class})
@ConditionalOnProperty(
        prefix = "oss",
        name = "endpoint"
)
public class OssAutoConfiguration {


    public OssAutoConfiguration() {
        log.info("------ 阿里云 OSSClient 自动配置  ---------------------------------------");
    }


    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(OSSClient.class)
    public OSSClient ossClient(OssProperties ossProperties) {
        return new OSSClient(ossProperties.getEndpoint()
                , new DefaultCredentialProvider(ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret())
                , null);
    }

    @Bean
    @ConditionalOnMissingBean(IAliYunOssService.class)
    public IAliYunOssService aliYunOssService(OssProperties ossProperties, OSSClient client) {
        return new AliYunOssServiceImpl(ossProperties, client);
    }

}
