package com.zhengcheng.common.support;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * The interface My mapper.
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.support
 * @Description :
 * @date :    2019/2/2 10:02
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
