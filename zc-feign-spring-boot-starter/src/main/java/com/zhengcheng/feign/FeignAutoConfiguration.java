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
import com.zhengcheng.feign.strategy.MdcHystrixConcurrencyStrategy;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Feign 统一配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Slf4j
@EnableFeignClients("com.zhengcheng.**.feign.**")
@ConditionalOnClass(RequestInterceptor.class)
@Configuration
public class FeignAutoConfiguration implements RequestInterceptor {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

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

        log.info("------ Feign 自动配置  ---------------------------------------");
        log.info("------ Feign 日志级别改为INFO ");
        log.info("------ Feign RequestInterceptor add header X-ZHENGCHENG-TRACE-ID ");
        log.info("------ Feign Hystrix线程池隔离下，支持MDC日志链路跟踪");
        log.info("------ RestTemplate 配置 @LoadBalanced");
        log.info("-----------------------------------------------------------------");
    }
}
