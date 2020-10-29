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

    private final String CREATE_TIME = "createTime";
    private final String UPDATE_TIME = "updateTime";
    private final String DELETED = "deleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        if (metaObject.hasSetter(CREATE_TIME)) {
            Object createTime = this.getFieldValByName(CREATE_TIME, metaObject);
            if (createTime == null) {
                this.setFieldValByName(CREATE_TIME, now, metaObject);
            }
        }
        if (metaObject.hasSetter(UPDATE_TIME)) {
            Object updateTime = this.getFieldValByName(UPDATE_TIME, metaObject);
            if (updateTime == null) {
                this.setFieldValByName(UPDATE_TIME, now, metaObject);
            }
        }
        if (metaObject.hasSetter(DELETED)) {
            Object deleted = this.getFieldValByName(DELETED, metaObject);
            if (deleted == null) {
                this.setFieldValByName(DELETED, Boolean.FALSE, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        }
    }
}