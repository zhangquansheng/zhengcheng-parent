package com.zhengcheng.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhengcheng.common.holder.ZcUserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 自定义填充公共 name 字段
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Slf4j
public class BaseMetaObjectHandler implements MetaObjectHandler {

    private final String CREATE_TIME = "createTime";
    private final String CREATE_USER_ID = "createUserId";
    private final String UPDATE_TIME = "updateTime";
    private final String UPDATE_USER_ID = "updateUserId";
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
        if (metaObject.hasSetter(CREATE_USER_ID)) {
            Object createUserId = this.getFieldValByName(CREATE_USER_ID, metaObject);
            if (createUserId == null) {
                Long currentUserId = getCurrentUserId();
                log.debug("start insert fill createUserId:{}", currentUserId);
                this.setFieldValByName(CREATE_USER_ID, currentUserId, metaObject);
            }
        }
        if (metaObject.hasSetter(UPDATE_TIME)) {
            Object updateTime = this.getFieldValByName(UPDATE_TIME, metaObject);
            if (updateTime == null) {
                this.setFieldValByName(UPDATE_TIME, now, metaObject);
            }
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            Object updateUserId = this.getFieldValByName(UPDATE_USER_ID, metaObject);
            if (updateUserId == null) {
                Long currentUserId = getCurrentUserId();
                log.debug("start insert fill updateUserId:{}", currentUserId);
                this.setFieldValByName(UPDATE_USER_ID, currentUserId, metaObject);
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
            Object updateTime = this.getFieldValByName(UPDATE_TIME, metaObject);
            if (updateTime == null) {
                this.setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
            }
        }
        if (metaObject.hasSetter(UPDATE_USER_ID)) {
            Object updateUserId = this.getFieldValByName(UPDATE_USER_ID, metaObject);
            if (updateUserId == null) {
                Long currentUserId = getCurrentUserId();
                log.debug("start update fill updateUserId:{}", currentUserId);
                this.setFieldValByName(UPDATE_USER_ID, currentUserId, metaObject);
            }
        }
    }

    private Long getCurrentUserId() {
        Long userId = ZcUserContextHolder.getUserId();
        return Objects.nonNull(userId) ? userId : 0L;
    }
}