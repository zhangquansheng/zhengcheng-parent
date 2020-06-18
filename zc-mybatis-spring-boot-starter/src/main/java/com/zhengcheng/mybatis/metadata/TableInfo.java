package com.zhengcheng.mybatis.metadata;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrBuilder;
import com.zhengcheng.mybatis.enums.IdType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 数据库表反射信息
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Data
@Setter(AccessLevel.PACKAGE)
@Accessors(chain = true)
public class TableInfo {

    /**
     * 实体类型
     */
    private Class<?> entityType;
    /**
     * 表主键ID 类型
     */
    private IdType idType = IdType.AUTO;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表主键ID 字段名
     */
    private String keyColumn;
    /**
     * 表主键ID 属性名
     */
    private String keyProperty;
    /**
     * 表主键ID 属性类型
     */
    private Class<?> keyType;
    /**
     * 表字段信息列表
     */
    private List<TableFieldInfo> fieldList;

    public TableInfo(Class<?> entityType) {
        this.entityType = entityType;
    }

    void setFieldList(List<TableFieldInfo> fieldList) {
        this.fieldList = fieldList;
    }

    public String getAllFieldString() {
        StrBuilder fieldString = StrBuilder.create(this.keyColumn).append(" AS `").append(this.keyProperty).append("`,");
        if (CollectionUtil.isNotEmpty(this.fieldList)) {
            fieldList.forEach(tableFieldInfo -> {
                if (tableFieldInfo.isSelect()) {
                    fieldString.append(tableFieldInfo.getColumn()).append(" AS `").append(tableFieldInfo.getProperty()).append("`,");
                }
            });
            return fieldString.subString(0, fieldString.length() - 1);
        }
        return fieldString.toString();
    }
}
