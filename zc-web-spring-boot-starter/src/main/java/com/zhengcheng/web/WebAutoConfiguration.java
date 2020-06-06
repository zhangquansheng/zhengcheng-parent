package com.zhengcheng.web;

import com.zhengcheng.swagger.constant.SwaggerConstants;
import com.zhengcheng.web.interceptor.TraceIdInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web模块自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:59
 */
@Slf4j
@EnableWebMvc
@Configuration
@ComponentScan({
        "com.zhengcheng.web.advice",
        "com.zhengcheng.web.aspect"})
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name:appName}")
    private String name;

    public WebAutoConfiguration() {
        if (log.isDebugEnabled()) {
            log.debug("Web模块自动配置成功");
        }
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
