package com.zhengcheng.rsa.encrypt.annotation;

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

}
