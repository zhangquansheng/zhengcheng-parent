package com.zhengcheng.mvc.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 参数加解密
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.annotation
 * @Description :
 * @date :    2019/3/28 10:36
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SecurityParam {

    /**
     * 入参是否解密，默认解密
     */
    boolean inDecode() default true;

    /**
     * 出参是否加密，默认加密
     */
    boolean outEncode() default true;
}
