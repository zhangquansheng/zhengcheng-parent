package com.zhengcheng.mvc.annotation;

import com.zhengcheng.mvc.enumeration.MobileMaskType;

import java.lang.annotation.*;

/**
 * 手机号脱敏
 *
 * @author :    quansheng.zhang
 * @Filename :     MobileMask.java
 * @Package :     com.zhengcheng.upms.annation
 * @Description :
 * @date :    2019/1/26 7:18
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileMask {
    MobileMaskType type() default MobileMaskType.TOP7;
}
