package com.zhengcheng.data.elasticsearch.metadata;

import com.zhengcheng.data.elasticsearch.annotations.Analyzer;
import com.zhengcheng.data.elasticsearch.annotations.Field;
import com.zhengcheng.data.elasticsearch.annotations.FieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * DocumentFieldInfo
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 11:02
 */
@Getter
@ToString
@EqualsAndHashCode
public class DocumentFieldInfo {
    /**
     * 属性名
     */
    private final String property;
    /**
     * 属性类型
     */
    private final Class<?> propertyType;
    /**
     * 属性是否是 CharSequence 类型
     */
    private final boolean isCharSequence;
    /**
     * 字段数据类型
     */
    private final FieldType fieldType;

    /**
     * 关键字忽略字数
     */
    private final int ignoreAbove;

    /**
     * 索引分词器设置
     */
    private final Analyzer analyzer;

    /**
     * 搜索内容分词器设置
     */
    private final Analyzer searchAnalyzer;

    public DocumentFieldInfo(java.lang.reflect.Field field, Field documentField) {
        field.setAccessible(true);
        this.property = field.getName();
        this.propertyType = field.getType();
        this.isCharSequence = this.isCharSequence(this.propertyType);
        this.fieldType = documentField.type();
        this.ignoreAbove = documentField.ignoreAbove();
        this.analyzer = documentField.analyzer();
        this.searchAnalyzer = documentField.searchAnalyzer();
    }

    public DocumentFieldInfo(java.lang.reflect.Field field) {
        field.setAccessible(true);
        this.property = field.getName();
        this.propertyType = field.getType();
        this.isCharSequence = this.isCharSequence(this.propertyType);
        this.fieldType = FieldType.Auto;
        this.ignoreAbove = 256;
        this.analyzer = Analyzer.AUTO;
        this.searchAnalyzer = Analyzer.AUTO;
    }

    /**
     * 是否为CharSequence类型
     *
     * @param clazz
     *            class
     * @return true 为是 CharSequence 类型
     */
    private boolean isCharSequence(Class<?> clazz) {
        return clazz != null && CharSequence.class.isAssignableFrom(clazz);
    }

    /**
     * 获取ES字段的类型值
     * 
     * @return 类型值
     */
    public String getTypeValue() {
        if (fieldType.equals(FieldType.Auto)) {
            if (propertyType.equals(Integer.class) || propertyType.equals(Long.class)) {
                return "long";
            } else if (propertyType.equals(Date.class) || propertyType.equals(LocalDateTime.class)) {
                return "date";
            }
            return "text";
        }
        return fieldType.name().toLowerCase();
    }
}
