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

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName(GMT_CREATE, now, metaObject);
        this.setFieldValByName(GMT_MODIFIED, now, metaObject);
        this.setFieldValByName(DELETED, Boolean.FALSE, metaObject);
//        Object gmtCreate = getFieldValByName(GMT_CREATE, metaObject);
//        Object gmtModified = getFieldValByName(GMT_MODIFIED, metaObject);
//        Object deleted = getFieldValByName(DELETED, metaObject);
//        if (gmtCreate == null || gmtModified == null || deleted == null) {
//            LocalDateTime now = LocalDateTime.now();
//            if (gmtCreate == null) {
//                this.setFieldValByName(GMT_CREATE, now, metaObject);
//            }
//            if (gmtModified == null) {
//                this.setFieldValByName(GMT_MODIFIED, now, metaObject);
//            }
//            if (deleted == null) {
//                this.setFieldValByName(DELETED, Boolean.FALSE, metaObject);
//            }
//        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(GMT_MODIFIED, LocalDateTime.now(), metaObject);
    }
}