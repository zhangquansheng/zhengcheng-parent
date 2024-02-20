package com.zhengcheng.data.elasticsearch.metadata;

import cn.hutool.core.util.ReflectUtil;
import com.zhengcheng.data.elasticsearch.annotations.Analyzer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 文档反射信息
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 10:59
 */
@Data
@Setter(AccessLevel.PACKAGE)
@Accessors(chain = true)
public class DocumentInfo {
    /**
     * 实体类型
     */
    private Class<?> entityType;
    /**
     * 文档索引名称
     */
    private String indexName;
    /**
     * 索引类型
     */
    private String indexType;
    /**
     * 主分片数量
     */
    private Integer indexNumberOfShards;
    /**
     * 备份分片数量
     */
    private Integer indexNumberOfReplicas;

    /**
     * 文档 主键ID 属性名
     */
    private String keyProperty;
    /**
     * 文档主键ID 属性类型
     */
    private Class<?> keyType;

    /**
     * 索引分词器设置
     */
    private Analyzer analyzer;

    /**
     * 搜索内容分词器设置
     */
    private Analyzer searchAnalyzer;
    /**
     * 文档字段信息列表
     */
    private List<DocumentFieldInfo> fieldList;

    public DocumentInfo(Class<?> entityType) {
        this.entityType = entityType;
    }

    void setFieldList(List<DocumentFieldInfo> fieldList) {
        this.fieldList = fieldList;
    }

    /**
     * 获取索引的值
     *
     * @param obj Object
     * @return 索引的值
     */
    public String getIndexValue(Object obj) {
        return String.valueOf(ReflectUtil.getFieldValue(obj, keyProperty));
    }
}
