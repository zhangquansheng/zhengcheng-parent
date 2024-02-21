package com.zhengcheng.magic.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

/**
 * 登录结果
 *
 * @author : quansheng.zhang
 * @date : 2019/10/29 11:02
 */
@Getter
public enum LoginResultEnum {

    SUCCESS(0, "成功"),

    FAILURE(1, "失败");

    @EnumValue
    private final int value;

    private String desc;

    LoginResultEnum(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

}
