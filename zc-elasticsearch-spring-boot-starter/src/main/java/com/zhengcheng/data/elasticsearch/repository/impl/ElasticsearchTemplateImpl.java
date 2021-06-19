package com.zhengcheng.data.elasticsearch.repository.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhengcheng.common.web.PageCommand;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.data.elasticsearch.metadata.DocumentFieldInfo;
import com.zhengcheng.data.elasticsearch.metadata.DocumentInfo;
import com.zhengcheng.data.elasticsearch.metadata.DocumentInfoHelper;
import com.zhengcheng.data.elasticsearch.repository.ElasticsearchTemplate;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ElasticsearchTemplateImpl
 *
 * @author quansheng1.zhang
 * @since 2021/6/17 10:16
 */
@Slf4j
@ConditionalOnBean({RestHighLevelClient.class})
@Repository
public class ElasticsearchTemplateImpl<T> implements ElasticsearchTemplate<T> {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public PageInfo<T> page(SearchSourceBuilder sourceBuilder, PageCommand pageCommand, Class<T> clazz)
        throws IOException {
        // 禁止深度分页
        int maxResultWindow = 10000;
        if (pageCommand.getPageSize() * pageCommand.getPageNum() > maxResultWindow) {
            // 优化解决办法：限制操作行为，禁止跳跃翻页查询，这时可以使用scroll进行滚动查询。
            throw new RuntimeException("防止耗尽ES内存资源，产生OOM，禁止深度分页。");
        }

        if (Objects.isNull(sourceBuilder)) {
            sourceBuilder = new SearchSourceBuilder();
        }
        sourceBuilder.from((pageCommand.getPageNum() - 1) * pageCommand.getPageSize());
        sourceBuilder.size(pageCommand.getPageSize());

        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);
        SearchResponse searchResponse = search(documentInfo, sourceBuilder);

        PageInfo<T> pageInfo = PageInfo.empty(pageCommand);
        pageInfo.setTotal(searchResponseToTotalHits(searchResponse));
        pageInfo.setList(searchResponseToList(clazz, documentInfo, searchResponse));
        return pageInfo;
    }

    @Override
    public PageInfo<T> page(PageCommand pageCommand, Class<T> clazz) throws IOException {
        return page(null, pageCommand, clazz);
    }

    @Override
    public List<T> list(Class<T> clazz) throws IOException {
        return list(null, clazz);
    }

    @Override
    public List<T> list(SearchSourceBuilder sourceBuilder, Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);
        SearchResponse searchResponse = search(documentInfo, sourceBuilder);
        return searchResponseToList(clazz, documentInfo, searchResponse);
    }

    @Override
    public void save(T t) throws IOException {
        IndexResponse indexResponse = client.index(getIndexRequest(t), RequestOptions.DEFAULT);
        log.info(StrUtil.format("变更文档记录结果：[{}]", indexResponse.getResult().toString()));
    }

    @Override
    public void batchSave(List<T> list) throws IOException {
        BulkRequest request = new BulkRequest();
        for (T t : list) {
            request.add(getIndexRequest(t));
        }
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        if (bulkResponse.hasFailures()) {
            log.error("ElasticsearchTemplate.batchSave one or more operation has failed");
        }
    }

    @Override
    public void delete(T t) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(t.getClass());

        DeleteRequest request =
            new DeleteRequest(documentInfo.getIndexName(), documentInfo.getIndexType(), documentInfo.getIndexValue(t));
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        log.info(StrUtil.format("删除文档记录结果：[{}]", deleteResponse.getResult().toString()));
    }

    @Override
    public void deleteById(String id, Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);

        DeleteRequest request = new DeleteRequest(documentInfo.getIndexName(), documentInfo.getIndexType(), id);
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        log.info(StrUtil.format("删除文档记录结果：[{}]", deleteResponse.getResult().toString()));
    }

    @Override
    public T getById(String id, Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);
        GetRequest getRequest = new GetRequest(documentInfo.getIndexName(), documentInfo.getIndexType(), id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        T t = string2Obj(getResponse.getSourceAsString(), clazz);
        setId(clazz, t, getResponse.getId(), documentInfo.getKeyProperty());
        return t;
    }

    @Override
    public boolean existsById(String id, Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);
        GetRequest getRequest = new GetRequest(documentInfo.getIndexName(), documentInfo.getIndexType(), id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return client.exists(getRequest, RequestOptions.DEFAULT);
    }

    private SearchResponse search(DocumentInfo documentInfo, SearchSourceBuilder sourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(documentInfo.getIndexName());
        searchRequest.types(documentInfo.getIndexType());

        if (Objects.nonNull(sourceBuilder)) {
            searchRequest.source(sourceBuilder);
        }
        if (log.isDebugEnabled()) {
            log.debug("\n" + mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(mapper.readValue(sourceBuilder.toString(), Object.class)));
        }
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }

    private long searchResponseToTotalHits(SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        return hits.getTotalHits();
    }

    private List<T> searchResponseToList(Class<T> clazz, DocumentInfo documentInfo, SearchResponse searchResponse)
        throws IOException {
        List<T> tList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            T t = string2Obj(hit.getSourceAsString(), clazz);
            setId(clazz, t, hit.getId(), documentInfo.getKeyProperty());
            tList.add(t);
            // 设置高亮
            Map<String, HighlightField> map = hit.getHighlightFields();
            if (Objects.nonNull(map)) {
                map.values().forEach(highlightField -> {
                    try {
                        Field field = clazz.getDeclaredField(highlightField.getName());
                        field.setAccessible(true);
                        field.set(t, getFragmentStr(highlightField.fragments()));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.error(e.getMessage(), e);
                    }
                });
            }
        }
        return tList;
    }

    private String getFragmentStr(org.elasticsearch.common.text.Text[] fragments) {
        StringBuilder sb = new StringBuilder();
        for (org.elasticsearch.common.text.Text str : fragments) {
            sb.append(str.string());
        }
        return sb.toString();
    }

    private IndexRequest getIndexRequest(T t) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(t.getClass());

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            for (DocumentFieldInfo documentFieldInfo : documentInfo.getFieldList()) {
                if (LocalDateTime.class.equals(documentFieldInfo.getPropertyType())) {
                    LocalDateTime localDateTime =
                        (LocalDateTime)ReflectUtil.getFieldValue(t, documentFieldInfo.getProperty());
                    builder.field(documentFieldInfo.getProperty(), LocalDateTimeUtil.formatNormal(localDateTime));
                } else {
                    builder.field(documentFieldInfo.getProperty(),
                        ReflectUtil.getFieldValue(t, documentFieldInfo.getProperty()));
                }
            }
        }
        builder.endObject();
        return new IndexRequest(documentInfo.getIndexName(), documentInfo.getIndexType(), documentInfo.getIndexValue(t))
            .source(builder);
    }

    @SuppressWarnings("TypeParameterHidesVisibleType")
    private <T> T string2Obj(String str, Class<T> clazz) throws JsonProcessingException {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        return clazz.equals(String.class) ? (T)str : mapper.readValue(str, clazz);
    }

    /**
     * 将 _id 字段的值 设置到 t 的 @Id 注解的属性上
     */
    private void setId(Class<T> clazz, T t, Object _id, String keyProperty) {
        try {
            Field field = clazz.getDeclaredField(keyProperty);
            field.setAccessible(true);
            if (field.get(t) == null) {
                field.set(t, _id);
            }
        } catch (Exception e) {
            log.error("setId error!", e);
        }
    }

}
