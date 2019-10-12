package com.zhengcheng.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.zhengcheng.oss.properties.OssProperties;
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
@EnableConfigurationProperties({OssProperties.class})
public class AliyunOSSAutoConfiguration {

    /**
     * 阿里云文件存储client
     * 只有配置了aliyun.oss.access-key才可以使用
     *
     * @param ossProperties 属性
     * @return
     */
    @Bean
    public OSSClient ossClient(OssProperties ossProperties) {
        OSSClient ossClient = new OSSClient(ossProperties.getEndpoint()
                , new DefaultCredentialProvider(ossProperties.getAccessKey(), ossProperties.getAccessKeySecret())
                , null);
        return ossClient;
    }

}
