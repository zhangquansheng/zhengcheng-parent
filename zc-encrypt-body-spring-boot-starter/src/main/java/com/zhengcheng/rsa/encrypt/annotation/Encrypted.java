package com.zhengcheng.rsa.encrypt.annotation;

import com.zhengcheng.rsa.encrypt.enums.EncryptBodyMethod;

import java.lang.annotation.*;

/**
 * 对 @Req@ResponseBody 返回对象加密
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 10:07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypted {

    /**
     * 加密方式
     */
    EncryptBodyMethod value() default EncryptBodyMethod.AES;

}
