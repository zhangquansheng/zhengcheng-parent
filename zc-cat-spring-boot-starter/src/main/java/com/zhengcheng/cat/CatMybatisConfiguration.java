package com.zhengcheng.cat;

import com.zhengcheng.cat.plugin.CatMybatisInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CatMybatisConfiguration
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 23:53
 */
@Configuration
public class CatMybatisConfiguration {

    public CatMybatisConfiguration() {
    }

    @Bean
    public CatMybatisInterceptor catMybatisInterceptor() {
        return new CatMybatisInterceptor();
    }
}
