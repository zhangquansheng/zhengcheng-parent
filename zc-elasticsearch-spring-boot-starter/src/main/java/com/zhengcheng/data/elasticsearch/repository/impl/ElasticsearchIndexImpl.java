package com.zhengcheng.data.elasticsearch.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.data.elasticsearch.annotations.Analyzer;
import com.zhengcheng.data.elasticsearch.metadata.DocumentFieldInfo;
import com.zhengcheng.data.elasticsearch.metadata.DocumentInfo;
import com.zhengcheng.data.elasticsearch.metadata.DocumentInfoHelper;
import com.zhengcheng.data.elasticsearch.repository.ElasticsearchIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Indices APIs
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 20:32
 */
@Slf4j
@ConditionalOnBean(RestHighLevelClient.class)
@Repository
public class ElasticsearchIndexImpl<T> implements ElasticsearchIndex<T> {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void create(Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);

        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.4/java-rest-high-create-index.html
        CreateIndexRequest request = new CreateIndexRequest(documentInfo.getIndexName());
        request.settings(Settings.builder().put("index.number_of_shards", documentInfo.getIndexNumberOfShards())
            .put("index.number_of_replicas", documentInfo.getIndexNumberOfReplicas()));
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject(documentInfo.getIndexType());
            {
                builder.startObject("properties");
                {
                    for (DocumentFieldInfo documentFieldInfo : documentInfo.getFieldList()) {
                        builder.startObject(documentFieldInfo.getProperty());
                        {
                            String typeValue = documentFieldInfo.getTypeValue();
                            builder.field("type", typeValue);

                            // text处理
                            if ("text".equals(typeValue)) {
                                // 指定分词器
                                builder.field("analyzer",
                                    getAnalyzer(documentInfo.getAnalyzer(), documentFieldInfo.getAnalyzer()));
                                builder.field("search_analyzer", getAnalyzer(documentInfo.getSearchAnalyzer(),
                                    documentFieldInfo.getSearchAnalyzer()));

                                builder.startObject("fields");
                                {
                                    builder.startObject("keyword");
                                    {
                                        builder.field("type", "keyword");
                                        builder.field("ignore_above", documentFieldInfo.getIgnoreAbove());
                                    }
                                    builder.endObject();
                                }
                                builder.endObject();
                            } else if ("date".equals(typeValue)) {
                                // https://www.elastic.co/guide/en/elasticsearch/reference/current/date.html
                                builder.field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");
                            }
                        }
                        builder.endObject();
                    }
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(documentInfo.getIndexType(), builder);

        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = createIndexResponse.isAcknowledged();
        log.info(StrUtil.format("创建索引[{}]结果：[{}]", documentInfo.getIndexName(), acknowledged));
    }

    @Override
    public void delete(Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);
        DeleteIndexRequest request = new DeleteIndexRequest(documentInfo.getIndexName());
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean acknowledged = deleteIndexResponse.isAcknowledged();
        log.info(StrUtil.format("删除索引[{}]结果：[{}]", documentInfo.getIndexName(), acknowledged));
    }

    @Override
    public boolean exists(Class<T> clazz) throws IOException {
        DocumentInfo documentInfo = DocumentInfoHelper.getDocumentInfo(clazz);
        GetIndexRequest request = new GetIndexRequest();
        request.indices(documentInfo.getIndexName());
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    private String getAnalyzer(Analyzer documentAnalyzer, Analyzer documentFieldAnalyzer) {
        if (!documentFieldAnalyzer.equals(Analyzer.AUTO)) {
            return StrUtil.toUnderlineCase(documentFieldAnalyzer.toString()).toLowerCase();
        }
        if (!documentAnalyzer.equals(Analyzer.AUTO)) {
            return StrUtil.toUnderlineCase(documentAnalyzer.toString()).toLowerCase();
        }
        return "standard";
    }
}
