package com.zhengcheng.satoken.annotation;

import com.zhengcheng.mvc.EnableUserInfoTransmittableAutoConfiguration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户信息传输
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 19:31
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EnableUserInfoTransmittableAutoConfiguration.class})
public @interface EnableUserInfoTransmittable {
}
