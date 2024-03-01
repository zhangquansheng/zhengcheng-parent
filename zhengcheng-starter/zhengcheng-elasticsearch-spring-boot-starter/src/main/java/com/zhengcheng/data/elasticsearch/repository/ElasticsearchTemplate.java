package com.zhengcheng.data.elasticsearch.repository;

import com.zhengcheng.common.domain.PageQuery;
import com.zhengcheng.common.domain.PageResult;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;


/**
 * Search APIs
 *
 * @author quansheng1.zhang
 * @since 2021/6/17 10:03
 */
public interface ElasticsearchTemplate<T> {

    PageResult<T> page(SearchSourceBuilder sourceBuilder, PageQuery pageQuery, Class<T> clazz) throws IOException;

    PageResult<T> page(PageQuery pageQuery, Class<T> clazz) throws IOException;

    List<T> list(Class<T> clazz) throws IOException;

    List<T> list(SearchSourceBuilder sourceBuilder, Class<T> clazz) throws IOException;

    void save(T t) throws IOException;

    void batchSave(List<T> list) throws IOException;

    void delete(T t) throws IOException;

    void deleteById(String id, Class<T> clazz) throws IOException;

    T getById(String id, Class<T> clazz) throws IOException;

    boolean existsById(String id, Class<T> clazz) throws IOException;
}
