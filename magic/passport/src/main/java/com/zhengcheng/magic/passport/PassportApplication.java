package com.zhengcheng.magic.passport;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author zhangquansheng
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@SpringCloudApplication
public class PassportApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(PassportApplication.class).run(args);
    }

    /**
     * 注入BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
