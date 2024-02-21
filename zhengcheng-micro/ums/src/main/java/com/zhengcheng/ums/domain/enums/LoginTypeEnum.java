package com.zhengcheng.ums.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

/**
 * 登录类型
 *
 * @author : quansheng.zhang
 * @date : 2019/10/29 11:00
 */
@Getter
public enum LoginTypeEnum {

    LOGIN(0, "系统登录"),

    LOGOUT(1, "系统登出");

    @EnumValue
    private final int    value;

    private final String desc;

    LoginTypeEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 根据value获取类型
     *
     * @param value
     *            值
     * @return 枚举
     */
    public static LoginTypeEnum getByValue(Integer value) {
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (value.equals(loginTypeEnum.getValue())) {
                return loginTypeEnum;
            }
        }
        return LoginTypeEnum.LOGIN;
    }
}
