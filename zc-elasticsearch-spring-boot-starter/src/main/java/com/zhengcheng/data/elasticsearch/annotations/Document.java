package com.zhengcheng.data.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * Document
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 10:54
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Document {

    /**
     * 索引名称，必须配置
     */
    String indexName();

    /**
     * 索引类型
     */
    String indexType() default "_doc";

    boolean useServerConfiguration() default false;

    /**
     * 主分片数量
     */
    int shards() default 5;

    /**
     * 备份分片数量
     */
    int replicas() default 1;

    String refreshInterval() default "1s";

    String indexStoreType() default "fs";

    boolean createIndex() default true;

    /**
     * 索引分词器设置
     */
    Analyzer analyzer() default Analyzer.AUTO;

    /**
     * 搜索内容分词器设置
     */
    Analyzer searchAnalyzer() default Analyzer.AUTO;
}
