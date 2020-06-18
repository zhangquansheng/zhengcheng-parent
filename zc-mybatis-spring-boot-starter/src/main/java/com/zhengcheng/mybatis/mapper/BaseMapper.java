package com.zhengcheng.mybatis.mapper;


import com.zhengcheng.mybatis.mapper.provider.DefaultSqlProvider;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * <p>这个 Mapper 支持 id 泛型</p>
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:30
 */
public interface BaseMapper<T> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 新增的记录个数
     */
    @InsertProvider(type = DefaultSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(T entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     * @return 被删除记录个数
     */
    @DeleteProvider(type = DefaultSqlProvider.class, method = "deleteById")
    int deleteById(Serializable id);

    /**
     * 根据 ID列表 批量删除
     *
     * @param id 主键ID列表
     * @return 被删除记录个数
     */
    @DeleteProvider(type = DefaultSqlProvider.class, method = "deleteBatchIds")
    int deleteBatchIds(@Param("id") Collection<? extends Serializable> id);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     * @return 被修改记录个数
     */
    @UpdateProvider(type = DefaultSqlProvider.class, method = "updateById")
    int updateById(T entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     * @return 实体
     */
    @SelectProvider(type = DefaultSqlProvider.class, method = "selectById")
    T selectById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param id 主键ID列表(不能为 null 以及 empty)
     * @return 实体
     */
    @SelectProvider(type = DefaultSqlProvider.class, method = "selectBatchIds")
    List<T> selectBatchIds(@Param("id") Collection<? extends Serializable> id);

    /**
     * 查询所有
     *
     * @return 实体
     */
    @SelectProvider(type = DefaultSqlProvider.class, method = "list")
    List<T> list();
}
