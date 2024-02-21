package com.zhengcheng.magic.domain.enums;

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
    private final int value;

    private String desc;

    LoginTypeEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

}
