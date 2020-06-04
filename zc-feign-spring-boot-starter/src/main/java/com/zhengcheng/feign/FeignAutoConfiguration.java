package com.zhengcheng.feign;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.util.SignAuthUtils;
import com.zhengcheng.feign.config.FeignOkHttpConfig;
import com.zhengcheng.feign.strategy.MdcHystrixConcurrencyStrategy;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Feign统一配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Slf4j
@Import({FeignOkHttpConfig.class})
@EnableFeignClients("com.zhengcheng.**.feign.**")
@ConditionalOnClass(RequestInterceptor.class)
@Configuration
public class FeignAutoConfiguration implements RequestInterceptor {

    @Value("${security.api.key:zhengcheng}")
    private String key;

    /**
     * Feign 日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 自定义INFO日志
     */
    @Bean
    FeignLoggerFactory infoFeignLoggerFactory() {
        return new InfoFeignLoggerFactory();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String traceId = MDC.get(CommonConstants.TRACE_ID);
        if (StrUtil.isEmptyOrUndefined(traceId)) {
            // 一些接口的调用需要实现幂等，比如消息发送，如果使用requestId就可以方便服务方实现幂等
            requestTemplate.header(CommonConstants.TRACE_ID, IdUtil.fastSimpleUUID());
        } else {
            requestTemplate.header(CommonConstants.TRACE_ID, traceId);
        }

        // API接口防止参数篡改和重放攻击
        Map<String, Collection<String>> queries = requestTemplate.queries();
        Map<String, Object> params = new HashMap<>(64);
        for (Map.Entry<String, Collection<String>> query : queries.entrySet()) {
            for (String value : query.getValue()) {
                params.put(query.getKey(), value);
            }
        }
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = IdUtil.fastSimpleUUID();
        String qs = SignAuthUtils.sortQueryParamString(params);
        String sign = SignAuthUtils.signMd5(qs, timestamp, nonceStr, key);

        requestTemplate.header(CommonConstants.SIGN_AUTH_SIGNATURE, sign);
        requestTemplate.header(CommonConstants.SIGN_AUTH_TIMESTAMP, timestamp);
        requestTemplate.header(CommonConstants.SIGN_AUTH_NONCE_STR, nonceStr);

        // Feign OAuth2 拦截器
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2Authentication) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                requestTemplate.header("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + details.getTokenValue());
            }
        }
    }

    public FeignAutoConfiguration() {
        try {
            HystrixConcurrencyStrategy mdcTarget = new MdcHystrixConcurrencyStrategy();
            HystrixConcurrencyStrategy strategy = HystrixPlugins.getInstance().getConcurrencyStrategy();
            if (strategy instanceof MdcHystrixConcurrencyStrategy) {
                return;
            }
            HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
            HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
            HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
            HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
            HystrixPlugins.reset();
            HystrixPlugins.getInstance().registerConcurrencyStrategy(mdcTarget);
            HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        } catch (Exception e) {
            log.error("Failed to register Hystrix Concurrency Strategy", e);
        }
    }
}
