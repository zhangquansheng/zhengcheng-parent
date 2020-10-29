package com.zhengcheng.core.cat;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.zhengcheng.core.cat.plugin.mybatis.CatMybatisInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CatMybatisConfiguration
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 23:53
 */
@Configuration
@ConditionalOnClass({MybatisPlusProperties.class})
public class CatMybatisConfiguration {

    public CatMybatisConfiguration() {
    }

    @Bean
    public CatMybatisInterceptor catMybatisInterceptor() {
        return new CatMybatisInterceptor();
    }
}
