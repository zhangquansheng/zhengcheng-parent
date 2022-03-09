package com.zhengcheng.core.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * 启动新征程服务
 *
 * @author quansheng1.zhang
 * @since 2021/6/19 14:23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@SpringBootApplication
@EnableFeignClients
public @interface EnableZhengChengSpringBootService {
}
