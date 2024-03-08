package com.zhengcheng.mvc.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * CustomLocaleAutoConfiguration
 *
 * @author quansheng1.zhang
 * @since 2024/2/1 18:17
 */
@Configuration
public class CustomLocaleAutoConfiguration {

    // -- @Bean就代表在spring配置文件里面使用<bean>标签
    // -- 这个实例化接口名字必须和LocaleResolver接口名字一样，首字母小写
    @Bean
    public LocaleResolver localeResolver() {
        return new SeczoneLocaleResolver();
    }

}
