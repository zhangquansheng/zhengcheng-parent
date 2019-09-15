package com.zhengcheng.common.enumeration;

/**
 * Result
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
public enum CodeEnum {

    SUCCESS(200),
    ERROR(1);

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
