package com.zhengcheng.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.zhengcheng.aliyun.properties.AliyunProperties;
import com.zhengcheng.aliyun.properties.OssProperties;
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
@EnableConfigurationProperties({AliyunProperties.class, OssProperties.class})
@ConditionalOnProperty(
        prefix = "aliyun.oss",
        name = "endpoint"
)
public class OssAutoConfiguration {

    /**
     * 阿里云文件存储client
     *
     * @param aliyunProperties aliyun属性
     * @param ossProperties    OSS属性
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public OSSClient ossClient(AliyunProperties aliyunProperties, OssProperties ossProperties) {
        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint()
                , new DefaultCredentialProvider(aliyunProperties.getAccessKey(), aliyunProperties.getAccessKeySecret())
                , null);
        return ossClient;
    }

}
