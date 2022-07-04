package com.zhengcheng.core;

import com.zhengcheng.core.feign.InfoFeignLoggerFactory;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 新征程 核心模块自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:59
 */
@Slf4j
@EnableAsync
@EnableWebMvc
@Configuration
@ComponentScan({
        "com.zhengcheng.core.advice",
        "com.zhengcheng.core.aspect"})
public class ZhengChengCoreAutoConfiguration implements WebMvcConfigurer {

    public ZhengChengCoreAutoConfiguration() {
        log.info("------ 核心模块自动配置 --------------------------------------------------------------------------------------");
        log.info("------ ExceptionControllerAdvice 统一异常处理 （默认配置 zc.exception-controller-advice.enabled = true ）------");
        log.info("------ ControllerLogAspect 控制层日志打印---------------------------------------------------------------------");
        log.info("------ @EnableWebMvc 启动SpringMvc的配置----------------------------------------------------------------------");
        log.info("------ @EnableAsync 启动线程池配置----------------------------------------------------------------------------");
        log.info("-------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html", "/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    /**
     * Feign 日志级别
     */
    @Bean
    @ConditionalOnProperty(name = "feign.log.info.enabled", havingValue = "true", matchIfMissing = true)
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 自定义 INFO 日志
     */
    @Bean
    @ConditionalOnProperty(name = "feign.log.info.enabled", havingValue = "true", matchIfMissing = true)
    FeignLoggerFactory infoFeignLoggerFactory() {
        return new InfoFeignLoggerFactory();
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
