package com.zhengcheng.core.tencentcloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.zhengcheng.core.tencentcloud.cos.ITencentcloudCosService;
import com.zhengcheng.core.tencentcloud.cos.impl.TencentcloudCosServiceImpl;
import com.zhengcheng.core.tencentcloud.cos.properties.CosProperties;
import com.zhengcheng.core.tencentcloud.properties.TencentcloudProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云对象存储 COS 自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/4 20:44
 */
@Configuration
@EnableConfigurationProperties({TencentcloudProperties.class, CosProperties.class})
public class CosAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public COSClient cosClient(TencentcloudProperties tencentcloudProperties) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(tencentcloudProperties.getSecretId(), tencentcloudProperties.getSecretKey());
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(tencentcloudProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public ITencentcloudCosService tencentcloudCosService(CosProperties cosProperties, COSClient cosClient) {
        return new TencentcloudCosServiceImpl(cosProperties, cosClient);
    }
}
