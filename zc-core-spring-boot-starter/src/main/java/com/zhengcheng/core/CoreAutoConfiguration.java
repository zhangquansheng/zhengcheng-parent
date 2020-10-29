package com.zhengcheng.core;

import com.zhengcheng.common.constant.SwaggerConstants;
import com.zhengcheng.core.web.interceptor.TraceIdInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 核心模块自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:59
 */
@Slf4j
@EnableAsync
@EnableWebMvc
@Configuration
@ComponentScan({
        "com.zhengcheng.core.web.advice",
        "com.zhengcheng.core.web.aspect"})
public class CoreAutoConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name:appName}")
    private String name;

    public CoreAutoConfiguration() {
        log.info("------ 核心模块自动配置 ---------------------------------------");
        log.info("------ ExceptionControllerAdvice 统一异常处理 ");
        log.info("------ ControllerLogAspect 控制层日志打印");
        log.info("------ @EnableWebMvc 启动SpringMvc的配置");
        log.info("------ @EnableAsync 启动线程池配置");
        log.info("-----------------------------------------------------------------");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //添加拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor(name))//路径拦截器
                .addPathPatterns("/**")  //拦截的请求路径
                .excludePathPatterns("/static/*")// 忽略静态文件
                .excludePathPatterns("/")
                .excludePathPatterns("/csrf")
                .excludePathPatterns("/error")
                .excludePathPatterns(SwaggerConstants.PATTERNS);
    }

}
