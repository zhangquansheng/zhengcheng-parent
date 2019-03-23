package com.zhengcheng.common.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * The interface My mapper.
 *
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
