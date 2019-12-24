package com.zhengcheng.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自定义填充公共 name 字段
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
public class DateMetaObjectHandler implements MetaObjectHandler {
    private final String GMT_CREATE = "gmtCreate";
    private final String GMT_MODIFIED = "gmtModified";
    private final String DELETED = "deleted";

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object gmtCreate = getFieldValByName(GMT_CREATE, metaObject);
        Object gmtModified = getFieldValByName(GMT_MODIFIED, metaObject);
        Object deleted = getFieldValByName(DELETED, metaObject);
        if (gmtCreate == null || gmtModified == null || deleted == null) {
            LocalDateTime now = LocalDateTime.now();
            if (gmtCreate == null) {
                setFieldValByName(GMT_CREATE, now, metaObject);
            }
            if (gmtModified == null) {
                setFieldValByName(GMT_MODIFIED, now, metaObject);
            }
            if (deleted == null) {
                setFieldValByName(DELETED, Boolean.FALSE, metaObject);
            }
        }
    }

    /**
     * 更新填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //mybatis-plus版本2.0.9+
        setFieldValByName(GMT_MODIFIED, LocalDateTime.now(), metaObject);
    }
}