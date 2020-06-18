package com.zhengcheng.mybatis.metadata;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.mybatis.annotation.TableField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;

/**
 * 数据库表字段反射信息
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Getter
@ToString
@EqualsAndHashCode
public class TableFieldInfo {
    /**
     * 字段名
     */
    private final String column;
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
     * 是否进行 select 查询
     * <p>大字段可设置为 false 不加入 select 查询范围</p>
     */
    private boolean select = true;

    /**
     * 全新的 存在 TableField 注解时使用的构造函数
     */
    public TableFieldInfo(TableInfo tableInfo, Field field, TableField tableField) {
        field.setAccessible(true);
        this.property = field.getName();
        this.propertyType = field.getType();
        this.isCharSequence = this.isCharSequence(this.propertyType);
        this.select = tableField.select();
        String column = tableField.value();
        if (StrUtil.isBlank(column)) {
            column = StrUtil.toUnderlineCase(column);
        } else {
            StrUtil.toUnderlineCase(field.getName());
        }
        this.column = column;
    }

    /**
     * 不存在 TableField 注解时, 使用的构造函数
     */
    public TableFieldInfo(TableInfo tableInfo, Field field) {
        field.setAccessible(true);
        this.property = field.getName();
        this.propertyType = field.getType();
        this.isCharSequence = this.isCharSequence(this.propertyType);
        this.column = StrUtil.toUnderlineCase(field.getName());
    }


    /**
     * 是否为CharSequence类型
     *
     * @param clazz class
     * @return true 为是 CharSequence 类型
     */
    private boolean isCharSequence(Class<?> clazz) {
        return clazz != null && CharSequence.class.isAssignableFrom(clazz);
    }

}
