package com.zhengcheng.data.elasticsearch.repository;

import java.io.IOException;

/**
 * Indices APIs
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 20:31
 */
public interface ElasticsearchIndex<T> {
    /**
     * 创建索引
     * 
     * @param clazz
     *            目标类
     * @throws IOException
     *             异常
     */
    void create(Class<T> clazz) throws IOException;

    /**
     * 删除索引
     * 
     * @param clazz
     *            目标类
     * @throws IOException
     *             异常
     */
    void delete(Class<T> clazz) throws IOException;

    /**
     * Indices Exists API
     * 
     * @param clazz
     *            目标类
     * @return 是否存在
     * @throws IOException
     *             异常
     */
    boolean exists(Class<T> clazz) throws IOException;
}
