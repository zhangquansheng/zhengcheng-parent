package com.zhengcheng.cat;

import com.dianping.cat.servlet.CatFilter;
import com.zhengcheng.cat.plugins.CatMybatisInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * CatFilterConfigure
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/4 23:27
 */
@Import({CatMybatisInterceptor.class})
@Configuration
public class CatFilterConfigure {

    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatFilter filter = new CatFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }

}
