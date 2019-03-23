package com.zhengcheng.common.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * The interface My mapper.
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.mybatis
 * @Description :
 * @date :    2019/2/28 21:00
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
