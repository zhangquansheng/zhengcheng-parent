package com.zhengcheng.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.zhengcheng.aliyun.oss.properties.OssProperties;
import com.zhengcheng.aliyun.properties.AliYunProperties;
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
@Configuration
@EnableConfigurationProperties({AliYunProperties.class, OssProperties.class})
@ConditionalOnProperty(
        prefix = "aliyun",
        name = "endpoint"
)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OSSClient ossClient(AliYunProperties aliYunProperties) {
        OSSClient ossClient = new OSSClient(aliYunProperties.getEndpoint()
                , new DefaultCredentialProvider(aliYunProperties.getAccessKey(), aliYunProperties.getAccessKeySecret())
                , null);
        return ossClient;
    }

}
