package com.zhengcheng.feign;

import com.zhengcheng.feign.config.FeignOkHttpConfig;
import com.zhengcheng.feign.interceptor.OAuth2FeignRequestInterceptor;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * Feign统一配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Import({FeignOkHttpConfig.class, OAuth2FeignRequestInterceptor.class})
public class FeignAutoConfigure {

    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
