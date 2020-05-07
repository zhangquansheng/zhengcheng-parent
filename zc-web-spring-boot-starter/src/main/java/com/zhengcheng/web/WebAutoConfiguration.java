package com.zhengcheng.web;

import com.zhengcheng.web.aspect.ControllerLogAspect;
import com.zhengcheng.web.interceptor.TraceIdInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
@EnableWebMvc
@Configuration
@Import({ControllerLogAspect.class})
@ComponentScan("com.zhengcheng.web.advice")
public class WebAutoConfiguration implements WebMvcConfigurer {

    public WebAutoConfiguration() {
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //添加拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor())//路径拦截器
                .addPathPatterns("/**")//拦截的请求路径
                .excludePathPatterns("/error")//排除的请求路径
                .excludePathPatterns("/static/*");
    }
}
