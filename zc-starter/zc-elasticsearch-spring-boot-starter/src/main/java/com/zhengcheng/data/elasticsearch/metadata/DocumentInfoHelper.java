package com.zhengcheng.data.elasticsearch.metadata;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.zhengcheng.data.elasticsearch.annotations.Document;
import com.zhengcheng.data.elasticsearch.annotations.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文档反射信息辅助类
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 11:08
 */
@Slf4j
public class DocumentInfoHelper {

    /**
     * 储存反射类文档信息
     */
    private static final Map<Class<?>, DocumentInfo> DOCUMENT_INFO_CACHE = new ConcurrentHashMap<>();

    /**
     * 默认序列化版本uid
     */
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * <p>
     * 获取实体映射文档信息
     * </p>
     *
     * @param clazz 反射实体类
     * @return 数据库文档反射信息
     */
    public static DocumentInfo getDocumentInfo(Class<?> clazz) {
        if (clazz == null || clazz == String.class) {
            return null;
        }

        DocumentInfo documentInfo = DOCUMENT_INFO_CACHE.get(ClassUtils.getUserClass(clazz));
        if (null != documentInfo) {
            return documentInfo;
        }
        // 尝试获取父类缓存
        Class<?> currentClass = clazz;
        while (null == documentInfo && Object.class != currentClass) {
            currentClass = currentClass.getSuperclass();
            documentInfo = DOCUMENT_INFO_CACHE.get(ClassUtils.getUserClass(currentClass));
        }
        if (documentInfo != null) {
            DOCUMENT_INFO_CACHE.put(ClassUtils.getUserClass(clazz), documentInfo);
        }
        return documentInfo;
    }

    /**
     * <p>
     * 实体类反射获取文档信息【初始化】
     * </p>
     *
     * @param clazz 反射实体类
     * @return 数据库文档反射信息
     */
    public synchronized static DocumentInfo initDocumentInfo(Class<?> clazz) {
        DocumentInfo documentInfo = DOCUMENT_INFO_CACHE.get(clazz);
        if (documentInfo != null) {
            return documentInfo;
        }

        /* 没有获取到缓存信息,则初始化 */
        documentInfo = new DocumentInfo(clazz);

        /* 初始化文档索引相关 */
        initDocumentIndex(clazz, documentInfo);

        /* 初始化字段相关 */
        initTableFields(clazz, documentInfo);

        /* 放入缓存 */
        DOCUMENT_INFO_CACHE.put(clazz, documentInfo);

        return documentInfo;
    }

    /**
     * <p>
     * 初始化 文档数据库类型,文档索引名
     * </p>
     *
     * @param clazz        实体类
     * @param documentInfo 数据库文档反射信息
     */
    private static void initDocumentIndex(Class<?> clazz, DocumentInfo documentInfo) {
        Document document = clazz.getAnnotation(Document.class);
        if (document != null) {
            documentInfo.setIndexName(document.indexName());
            documentInfo.setIndexType(document.indexType());
            documentInfo.setIndexNumberOfShards(document.shards());
            documentInfo.setIndexNumberOfReplicas(document.replicas());
            documentInfo.setAnalyzer(document.analyzer());
            documentInfo.setSearchAnalyzer(document.searchAnalyzer());
        }
    }

    /**
     * <p>
     * 初始化 文档主键,文档字段
     * </p>
     *
     * @param clazz        实体类
     * @param documentInfo 数据库文档反射信息
     */
    public static void initTableFields(Class<?> clazz, DocumentInfo documentInfo) {
        Field[] list = ReflectUtil.getFields(clazz);
        // 是否存在 @Id 注解
        boolean existId = isExistId(Arrays.asList(list));
        if (!existId) {
            log.warn("请使用 @Id 标记 id 主键");
            throw new RuntimeException();
        }

        List<DocumentFieldInfo> fieldList = new ArrayList<>();
        for (Field field : list) {
            if (StrUtil.equalsIgnoreCase(SERIAL_VERSION_UID, field.getName())) {
                continue;
            }
            /* 文档 ID 初始化 */
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                initIndexIdWithoutAnnotation(documentInfo, field, clazz.getName());
                continue;
            }

            /* 有 @Field 注解的字段初始化 */
            if (initDocumentFieldWithAnnotation(fieldList, field)) {
                continue;
            }

            /* 无 @Field 注解的字段初始化 */
            fieldList.add(new DocumentFieldInfo(field));
        }

        /* 字段列文档,不可变集合 */
        documentInfo.setFieldList(Collections.unmodifiableList(fieldList));
    }

    /**
     * <p>
     * 文档主键属性初始化
     * </p>
     *
     * @param documentInfo 文档信息
     * @param field        字段
     * @param className    类名称
     */
    private static void initIndexIdWithoutAnnotation(DocumentInfo documentInfo, Field field, String className) {
        if (StrUtil.isNotEmpty(documentInfo.getKeyProperty())) {
            log.error("@Id can't more than one in Class: {}.", className);
            throw new RuntimeException();
        }
        if (!String.class.equals(field.getType())) {
            log.error("@Id only String type is supported in Class: {}.", className);
            throw new RuntimeException();
        }

        final String property = field.getName();
        documentInfo.setKeyProperty(property).setKeyType(field.getType());
    }

    /**
     * <p>
     * 字段属性初始化
     * </p>
     *
     * @param fieldList 字段列文档
     * @return true 继续下一个属性判断，返回 continue;
     */
    private static boolean initDocumentFieldWithAnnotation(List<DocumentFieldInfo> fieldList, Field field) {
        /* 获取注解属性，自定义字段 */
        com.zhengcheng.data.elasticsearch.annotations.Field documentField =
                field.getAnnotation(com.zhengcheng.data.elasticsearch.annotations.Field.class);
        if (Objects.isNull(documentField)) {
            return false;
        }

        fieldList.add(new DocumentFieldInfo(field, documentField));
        return true;
    }

    /**
     * <p>
     * 判断主键注解是否存在
     * </p>
     *
     * @param list 字段列文档
     * @return true 为存在 @Id 注解;
     */
    public static boolean isExistId(List<Field> list) {
        return list.stream().anyMatch(field -> field.isAnnotationPresent(Id.class));
    }
}
