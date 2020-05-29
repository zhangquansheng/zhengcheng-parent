package com.zhengcheng.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.common.exception.IdempotentException;
import com.zhengcheng.common.exception.LockException;
import com.zhengcheng.common.lock.DistributedLock;
import com.zhengcheng.common.service.IBaseService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * service实现父类
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/23 0:50
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public boolean saveIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (Objects.isNull(lock)) {
            throw new LockException("DistributedLock is null");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }
        try {
            // 最大等待时间，默认为5s
            long MAX_WAIT_MILLIS = 5000;
            //加锁
            boolean isLock = lock.acquire(lockKey, MAX_WAIT_MILLIS, TimeUnit.MILLISECONDS);
            if (isLock) {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0) {
                    return super.save(entity);
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    throw new IdempotentException(msg);
                }
            } else {
                throw new LockException("锁等待超时");
            }
        } catch (Exception e) {
            log.error("saveIdempotent acquire lock exception: {}", e.getMessage(), e);
            throw new LockException("锁等待超时");
        } finally {
            try {
                lock.release(lockKey);
            } catch (Exception e) {
                log.error("saveIdempotent release lock exception: {}", e.getMessage(), e);
            }
        }
    }

    @Override
    public boolean saveIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
        return saveIdempotent(entity, lock, lockKey, countWrapper, null);
    }

    @Override
    public boolean saveOrUpdateIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StrUtil.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotent(entity, lock, lockKey, countWrapper, msg);
                } else {
                    return updateById(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotent(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) {
        return this.saveOrUpdateIdempotent(entity, lock, lockKey, countWrapper, null);
    }
}
