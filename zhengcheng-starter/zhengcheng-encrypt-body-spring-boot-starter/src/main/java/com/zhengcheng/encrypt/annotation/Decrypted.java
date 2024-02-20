package com.zhengcheng.encrypt.annotation;

import com.zhengcheng.encrypt.enums.DecryptBodyMethod;

import java.lang.annotation.*;

/**
 * 对 @RequestBody 请求对象解密
 *
 * @author quansheng1.zhang
 * @since 2022/10/20 11:07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypted {

    DecryptBodyMethod value() default DecryptBodyMethod.AES;
}
