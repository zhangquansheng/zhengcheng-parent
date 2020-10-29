package com.zhengcheng.core.mybatis.metadata;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.zhengcheng.core.mybatis.annotation.TableField;
import com.zhengcheng.core.mybatis.annotation.TableId;
import com.zhengcheng.core.mybatis.annotation.TableName;
import com.zhengcheng.core.mybatis.util.ReflectionKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体类反射表辅助类
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Slf4j
public class TableInfoHelper {

    /**
     * 储存反射类表信息
     */
    private static final Map<Class<?>, TableInfo> TABLE_INFO_CACHE = new ConcurrentHashMap<>();

    /**
     * 默认序列化版本uid
     */
    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 默认表主键名称
     */
    private static final String DEFAULT_ID_NAME = "id";

    /**
     * <p>
     * 获取实体映射表信息
     * </p>
     *
     * @param clazz 反射实体类
     * @return 数据库表反射信息
     */
    public static TableInfo getTableInfo(Class<?> clazz) {
        if (clazz == null
                || ReflectionKit.isPrimitiveOrWrapper(clazz)
                || clazz == String.class) {
            return null;
        }
        TableInfo tableInfo = TABLE_INFO_CACHE.get(ClassUtils.getUserClass(clazz));
        if (null != tableInfo) {
            return tableInfo;
        }
        //尝试获取父类缓存
        Class<?> currentClass = clazz;
        while (null == tableInfo && Object.class != currentClass) {
            currentClass = currentClass.getSuperclass();
            tableInfo = TABLE_INFO_CACHE.get(ClassUtils.getUserClass(currentClass));
        }
        if (tableInfo != null) {
            TABLE_INFO_CACHE.put(ClassUtils.getUserClass(clazz), tableInfo);
        }
        return tableInfo;
    }

    /**
     * <p>
     * 实体类反射获取表信息【初始化】
     * </p>
     *
     * @param clazz 反射实体类
     * @return 数据库表反射信息
     */
    public synchronized static TableInfo initTableInfo(Class<?> clazz) {
        TableInfo tableInfo = TABLE_INFO_CACHE.get(clazz);
        if (tableInfo != null) {
            return tableInfo;
        }

        /* 没有获取到缓存信息,则初始化 */
        tableInfo = new TableInfo(clazz);

        /* 初始化表名相关 */
        initTableName(clazz, tableInfo);

        /* 初始化字段相关 */
        initTableFields(clazz, tableInfo);

        /* 放入缓存 */
        TABLE_INFO_CACHE.put(clazz, tableInfo);

        return tableInfo;
    }

    /**
     * <p>
     * 初始化 表数据库类型,表名,resultMap
     * </p>
     *
     * @param clazz     实体类
     * @param tableInfo 数据库表反射信息
     */
    private static void initTableName(Class<?> clazz, TableInfo tableInfo) {
        TableName table = clazz.getAnnotation(TableName.class);

        String tableName = clazz.getSimpleName();

        if (table != null) {
            if (StrUtil.isNotBlank(table.value())) {
                tableName = table.value();
            } else {
                tableName = StrUtil.toUnderlineCase(tableName);
            }
        } else {
            tableName = StrUtil.toUnderlineCase(tableName);
        }
        tableInfo.setTableName(tableName);

    }

    /**
     * <p>
     * 初始化 表主键,表字段
     * </p>
     *
     * @param clazz     实体类
     * @param tableInfo 数据库表反射信息
     */
    public static void initTableFields(Class<?> clazz, TableInfo tableInfo) {
        Field[] list = ReflectUtil.getFields(clazz);
        // 是否存在 @TableId 注解
        boolean existTableId = isExistTableId(Arrays.asList(list));
        if (!existTableId) {
            log.warn("请使用 @TableId 标记 id 主键");
            throw new RuntimeException();
        }

        List<TableFieldInfo> fieldList = new ArrayList<>();
        for (Field field : list) {
            if (StrUtil.equalsIgnoreCase(SERIAL_VERSION_UID, field.getName())) {
                continue;
            }
            /* 主键ID 初始化 */
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                initTableIdWithoutAnnotation(tableInfo, field, tableId, clazz.getName());
                continue;
            }

            /* 有 @TableField 注解的字段初始化 */
            if (initTableFieldWithAnnotation(tableInfo, fieldList, field)) {
                continue;
            }

            /* 无 @TableField 注解的字段初始化 */
            fieldList.add(new TableFieldInfo(tableInfo, field));
        }

        /* 字段列表,不可变集合 */
        tableInfo.setFieldList(Collections.unmodifiableList(fieldList));
    }

    /**
     * <p>
     * 主键属性初始化
     * </p>
     *
     * @param tableInfo 表信息
     * @param field     字段
     */
    private static void initTableIdWithoutAnnotation(TableInfo tableInfo, Field field, TableId tableId, String className) {
        if (StrUtil.isNotEmpty(tableInfo.getKeyProperty())) {
            log.warn("@TableId can't more than one in Class: {}.", className);
            throw new RuntimeException();
        }
        final String property = field.getName();
        if (DEFAULT_ID_NAME.equalsIgnoreCase(property)) {
            tableInfo.setIdType(tableId.type())
                    .setKeyColumn(property)
                    .setKeyProperty(property)
                    .setKeyType(field.getType());
        } else {
            log.warn(String.format("This \"%s\" is the table primary key by default name for `id` in Class: \"%s\",So @TableField will not work!",
                    property, tableInfo.getEntityType().getName()));
            throw new RuntimeException();
        }
    }

    /**
     * <p>
     * 字段属性初始化
     * </p>
     *
     * @param tableInfo 表信息
     * @param fieldList 字段列表
     * @return true 继续下一个属性判断，返回 continue;
     */
    private static boolean initTableFieldWithAnnotation(TableInfo tableInfo,
                                                        List<TableFieldInfo> fieldList, Field field) {
        /* 获取注解属性，自定义字段 */
        TableField tableField = field.getAnnotation(TableField.class);
        if (Objects.isNull(tableField)) {
            return false;
        }
        if (tableField.exist()) {
            fieldList.add(new TableFieldInfo(tableInfo, field, tableField));
        }
        return true;
    }

    /**
     * <p>
     * 判断主键注解是否存在
     * </p>
     *
     * @param list 字段列表
     * @return true 为存在 @TableId 注解;
     */
    public static boolean isExistTableId(List<Field> list) {
        return list.stream().anyMatch(field -> field.isAnnotationPresent(TableId.class));
    }
}
