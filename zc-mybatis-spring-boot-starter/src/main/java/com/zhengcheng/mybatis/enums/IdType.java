package com.zhengcheng.mybatis.enums;

import lombok.Getter;

/**
 * 生成ID类型枚举类
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/27 22:21
 */
@Getter
public enum IdType {

    /**
     * 数据库ID自增
     */
    AUTO(0);

    private final int key;

    IdType(int key) {
        this.key = key;
    }
}
