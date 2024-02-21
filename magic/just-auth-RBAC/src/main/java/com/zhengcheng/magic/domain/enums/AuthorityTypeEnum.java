package com.zhengcheng.magic.domain.enums;

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

    MENU(0, "菜单"),

    BUTTON(1, "按钮");

    @EnumValue
    private final int value;

    private String desc;

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
