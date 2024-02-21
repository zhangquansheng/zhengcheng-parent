package com.zhengcheng.ums.common.config;

import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.ums.common.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * UmsMvcConfiguration
 *
 * @author :    zhangquansheng
 * @date :    2022/07/02 11:16
 */
@Configuration
public class UmsMvcConfiguration implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = CommonConstants.CHECK_LOGIN_EXCLUDE_PATH_PATTERNS;
        patterns.add("/**/user/login/**");
        patterns.add("/**/user/mobile/login/**");
        patterns.add("/**/user/sendLoginSms");
        patterns.add("/**/user/submit");
        patterns.add("/**/oauth/**");
        patterns.add("/**/crypto/**");
        patterns.add("/**/kaptcha/**");
        patterns.add("/**/favicon.ico");

        registry.addInterceptor(userInterceptor()).addPathPatterns("/**").excludePathPatterns(patterns);
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }
}
