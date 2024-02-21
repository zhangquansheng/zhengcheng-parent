package com.zhengcheng.ums;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * UmsApplication
 *
 * @author quansheng1.zhang
 * @since 2022/3/8 17:36
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UmsApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UmsApplication.class).run(args);
    }

    /**
     * 注入BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
