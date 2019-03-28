package com.zhengcheng.mvc.annotation;

import com.zhengcheng.mvc.enumeration.SecurityParamType;

import java.lang.annotation.*;

/**
 * 参数加解密
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.annotation
 * @Description :
 * @date :    2019/3/28 10:36
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityParam {
    SecurityParamType type() default SecurityParamType.ENCRYPT;
}
