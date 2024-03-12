package com.zhengcheng.satoken;

import com.zhengcheng.satoken.impl.StpInterfaceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * SaTokenAutoConfiguration
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:59
 */
@Slf4j
@Configuration
@ComponentScan({"com.zhengcheng.satoken.advice"})
public class SaTokenAutoConfiguration {

    public SaTokenAutoConfiguration() {

    }

    @Bean
    public StpInterfaceImpl stpInterface() {
        return new StpInterfaceImpl();
    }
}
