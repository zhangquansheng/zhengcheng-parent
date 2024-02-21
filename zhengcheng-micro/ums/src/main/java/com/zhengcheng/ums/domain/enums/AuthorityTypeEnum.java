package com.zhengcheng.ums.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.Getter;

/**
 * AuthorityTypeEnum
 *
 * @author quansheng1.zhang
 * @since 2021/8/13 14:47
 */
@Getter
public enum AuthorityTypeEnum {

    CATALOGUE(0,"目录"),

    MENU(1, "菜单"),

    BUTTON(2, "按钮");

    @EnumValue
    private final int value;

    private final String desc;

    AuthorityTypeEnum(final int value, final String desc) {
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
    public static AuthorityTypeEnum getByValue(Integer value) {
        for (AuthorityTypeEnum authorityTypeEnum : AuthorityTypeEnum.values()) {
            if (value.equals(authorityTypeEnum.getValue())) {
                return authorityTypeEnum;
            }
        }
        return AuthorityTypeEnum.MENU;
    }

}
