package com.zhengcheng.mybatis.plus.config;

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

    private final String GMT_MODIFIED = "gmtModified";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        String GMT_CREATE = "gmtCreate";
        if (metaObject.hasSetter(GMT_CREATE)) {
            Object gmtCreate = this.getFieldValByName(GMT_CREATE, metaObject);
            if (gmtCreate == null) {
                this.setFieldValByName(GMT_CREATE, now, metaObject);
            }
        }
        if (metaObject.hasSetter(GMT_MODIFIED)) {
            Object gmtModified = this.getFieldValByName(GMT_MODIFIED, metaObject);
            if (gmtModified == null) {
                this.setFieldValByName(GMT_MODIFIED, now, metaObject);
            }
        }
        String DELETED = "deleted";
        if (metaObject.hasSetter(DELETED)) {
            Object deleted = this.getFieldValByName(DELETED, metaObject);
            if (deleted == null) {
                this.setFieldValByName(DELETED, Boolean.FALSE, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(GMT_MODIFIED)) {
            this.setFieldValByName(GMT_MODIFIED, LocalDateTime.now(), metaObject);
        }
    }
}