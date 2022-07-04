package com.zhengcheng.core.annotation;

import com.zhengcheng.core.EnableUserInfoTransmittableAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

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
