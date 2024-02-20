package com.zhengcheng.core.feign.config;

import feign.Feign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(FeignAutoConfiguration.class)
@RequiredArgsConstructor
public class FeignOkHttpConfig {

    @Value("${feign.okhttp3.read-timeout.milliseconds:500}")
    private Long readTimeout;
    @Value("${feign.okhttp3.connect-timeout.milliseconds:1000}")
    private Long connectTimeout;
    @Value("${feign.okhttp3.write-timeout.milliseconds:60000}")
    private Long writeTimeout;

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        log.info("-----  注入 OkHttpClient ---------------------------------------------------------------------");
        log.info("-----  readTimeout = {}ms ---------------------------------------------------------------------", readTimeout);
        log.info("-----  connectTimeout = {}ms ---------------------------------------------------------------------", connectTimeout);
        log.info("-----  writeTimeout = {}ms ---------------------------------------------------------------------", writeTimeout);
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
//                .retryOnConnectionFailure(false)
                // 使用okhttp3.ConnectionPool，Connection:keep-Alive 的时间为5分钟
                .connectionPool(new okhttp3.ConnectionPool())
                .build();
    }
}