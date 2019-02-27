package com.zhengcheng.mvc.annotation;

import java.lang.annotation.*;

/**
 * ID加密
 *
 * @author :    quansheng.zhang
 * @Filename :     IDEncrypt.java
 * @Package :     com.zhengcheng.mvc.annotation
 * @Description :
 * @date :    2019/1/31 22:07
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptId {
}
