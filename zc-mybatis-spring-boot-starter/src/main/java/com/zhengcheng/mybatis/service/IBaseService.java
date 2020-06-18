package com.zhengcheng.mybatis.service;


import com.zhengcheng.mybatis.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 顶级 Service
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:36
 */
public interface IBaseService<T> {

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 新增的记录个数
     */
    int insert(T entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return 被删除记录个数
     */
    int deleteById(Serializable id);

    /**
     * 根据 ID列表 批量删除
     *
     * @param id 主键ID列表
     * @return 被删除记录个数
     */
    int deleteBatchIds(Collection<? extends Serializable> id);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return 被修改记录个数
     */
    int updateById(T entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return 实体
     */
    T selectById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param id 主键ID列表(不能为 null 以及 empty)
     * @return 实体
     */
    List<T> selectBatchIds(Collection<? extends Serializable> id);

    /**
     * 查询所有
     *
     * @return 实体
     */
    List<T> list();
}
