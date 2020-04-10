package com.zhengcheng.feign;

import cn.hutool.core.util.IdUtil;
import com.zhengcheng.feign.config.FeignOkHttpConfig;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Feign统一配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Import({FeignOkHttpConfig.class})
@EnableFeignClients("com.zhengcheng.**.feign.**")
@ConditionalOnClass(RequestInterceptor.class)
@Configuration
public class FeignAutoConfiguration implements RequestInterceptor {

    public static final String REQUEST_ID = "requestId";

    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 一些接口的调用需要实现幂等，比如消息发送，如果使用requestId就可以方便服务方实现幂等
        requestTemplate.header(FeignAutoConfiguration.REQUEST_ID, IdUtil.fastSimpleUUID());
    }

    /**
     * 取消重试
     */
    @Bean
    Retryer feignRetry() {
        return Retryer.NEVER_RETRY;
    }
}
