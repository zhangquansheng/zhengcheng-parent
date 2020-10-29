package com.zhengcheng.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateAutoConfigure
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 22:50
 */
@Configuration
public class RestTemplateAutoConfiguration {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
