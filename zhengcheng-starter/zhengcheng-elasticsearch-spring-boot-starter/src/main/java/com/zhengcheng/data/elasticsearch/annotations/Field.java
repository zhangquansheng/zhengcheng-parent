package com.zhengcheng.data.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * 对应索引结构mapping的注解，在es entity field上添加
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 10:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Field {

    /**
     * 数据类型（包含 关键字类型）
     */
    FieldType type() default FieldType.Auto;

    /**
     * 关键字忽略字数
     */
    int ignoreAbove() default 256;

    /**
     * 索引分词器设置
     */
    Analyzer analyzer() default Analyzer.AUTO;

    /**
     * 搜索内容分词器设置
     */
    Analyzer searchAnalyzer() default Analyzer.AUTO;
}
