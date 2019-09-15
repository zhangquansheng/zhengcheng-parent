package com.zhengcheng.swagger.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateAutoConfigure
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 22:50
 */
public class RestTemplateAutoConfigure {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
