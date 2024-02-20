package com.yhq.sensitive.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 手机号脱敏
 * @author yhq
 * @date 2021年9月7日 08点51分
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.yhq.sensitive.strategy.SensitiveMobile.class,pattern = "(?<=\\w{3})\\w(?=\\w{4})",replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveMobile {

}
