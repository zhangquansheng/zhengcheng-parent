package com.zhengcheng.core.cat;

import com.zhengcheng.core.cat.context.CatContextServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CatFilterConfigure
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/4 23:27
 */
@Configuration
public class CatFilterConfiguration {

    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatContextServletFilter filter = new CatContextServletFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }

}
