package com.zhengcheng.feign.config;

import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * FeignOkHttpConfig
 *
 * @author :    quansheng.zhang
 * @date :    2019/6/29 16:07
 */
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Value("${feign.okhttp3.read-timeout.milliseconds:500}")
    private Long readTimeout;
    @Value("${feign.okhttp3.connect-timeout.milliseconds:1000}")
    private Long connectTimeout;
    @Value("${feign.okhttp3.write-timeout.milliseconds:60000}")
    private Long writeTimeout;

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)
                .connectionPool(new okhttp3.ConnectionPool())
                .build();
    }
}
