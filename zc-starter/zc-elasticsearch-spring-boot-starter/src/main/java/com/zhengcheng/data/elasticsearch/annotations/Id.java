package com.zhengcheng.data.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * Document 主键标识
 *
 * @author : quansheng.zhang
 * @date : 2020/3/27 22:21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

}
