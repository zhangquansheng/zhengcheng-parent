package com.zhengcheng.ratelimit.enums;

import lombok.Getter;

/**
 * 限流类型
 *
 * @author quansheng1.zhang
 * @since 2020/12/8 20:51
 */
@Getter
public enum LimitType {
    /**
     * default
     */
    METHOD("METHOD", "针对指定接口方法访问限流"),

    IP("IP", "针对指定接口方法访问限流"),

    USER("USER", "针对指定接口方法访问限流"),

    ARGS("ARGS", "针对指定接口方法访问限流");

    private final String value;

    private final String desc;

    LimitType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
