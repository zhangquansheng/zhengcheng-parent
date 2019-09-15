package com.zhengcheng.db.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义填充公共 name 字段
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
public class DateMetaObjectHandler implements MetaObjectHandler {
    private final String GMT_CREATE = "gmtCreate";
    private final String GMT_MODIFIED = "gmtModified";

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object gmtCreate = getFieldValByName(GMT_CREATE, metaObject);
        Object gmtModified = getFieldValByName(GMT_MODIFIED, metaObject);
        if (gmtCreate == null || gmtModified == null) {
            Date date = new Date();
            if (gmtCreate == null) {
                setFieldValByName(GMT_CREATE, date, metaObject);
            }
            if (gmtModified == null) {
                setFieldValByName(GMT_MODIFIED, date, metaObject);
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
        setFieldValByName(GMT_MODIFIED, new Date(), metaObject);
    }
}