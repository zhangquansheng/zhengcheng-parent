package com.zhengcheng.gw.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * FeignConfig
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 17:58
 */
@Configuration
public class FeignConfig {

    @Bean
    public feign.codec.Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(
                new MappingJackson2HttpMessageConverter());
        return () -> httpMessageConverters;
    }
}
