package com.zhengcheng.core;

import com.zhengcheng.core.filter.TransmittableUserInfoFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * 在业务端通过注解 @EnableInfoContextTransmittable 加载
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 19:44
 */
@Slf4j
@RequiredArgsConstructor
public class EnableUserInfoTransmittableAutoConfiguration {

    @Bean
    public TransmittableUserInfoFilter transmittableUserInfoFromHttpHeader() {
        log.info("-----TransmittableUserInfoFilter");
        return new TransmittableUserInfoFilter();
    }

}
