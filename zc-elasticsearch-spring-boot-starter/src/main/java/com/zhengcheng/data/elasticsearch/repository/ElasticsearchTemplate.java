package com.zhengcheng.data.elasticsearch.repository;

import com.zhengcheng.common.web.PageCommand;
import com.zhengcheng.common.web.PageInfo;
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

    PageInfo<T> page(SearchSourceBuilder sourceBuilder, PageCommand pageCommand, Class<T> clazz) throws IOException;

    PageInfo<T> page(PageCommand pageCommand, Class<T> clazz) throws IOException;

    List<T> list(Class<T> clazz) throws IOException;

    List<T> list(SearchSourceBuilder sourceBuilder, Class<T> clazz) throws IOException;

    void save(T t) throws IOException;

    void batchSave(List<T> list) throws IOException;

    void delete(T t) throws IOException;

    void deleteById(String id, Class<T> clazz) throws IOException;

    T getById(String id, Class<T> clazz) throws IOException;

    boolean existsById(String id, Class<T> clazz) throws IOException;
}
