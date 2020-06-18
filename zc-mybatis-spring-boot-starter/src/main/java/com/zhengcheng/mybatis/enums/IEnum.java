package com.zhengcheng.mybatis.enums;

import java.io.Serializable;

/**
 * 自定义枚举接口
 *
 * @author :    quansheng.zhang
 * @date :    2020/04/01 15:21
 */
public interface IEnum<T extends Serializable> {

    /**
     * 枚举数据库存储值
     *
     * @return T
     */
    int getValue();
}
