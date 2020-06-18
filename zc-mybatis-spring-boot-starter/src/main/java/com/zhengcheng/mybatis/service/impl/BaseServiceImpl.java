package com.zhengcheng.mybatis.service.impl;

import com.zhengcheng.mybatis.mapper.BaseMapper;
import com.zhengcheng.mybatis.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * IBaseService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ）
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/29 12:52
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> implements IBaseService<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    @Override
    public int insert(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int deleteById(Serializable id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public int deleteBatchIds(Collection<? extends Serializable> id) {
        return baseMapper.deleteBatchIds(id);
    }

    @Override
    public int updateById(T entity) {
        return baseMapper.updateById(entity);
    }

    @Override
    public T selectById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> id) {
        return baseMapper.selectBatchIds(id);
    }

    @Override
    public List<T> list() {
        return baseMapper.list();
    }
}
