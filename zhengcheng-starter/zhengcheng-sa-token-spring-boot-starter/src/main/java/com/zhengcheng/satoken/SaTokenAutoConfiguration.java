package com.zhengcheng.satoken;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

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
}
