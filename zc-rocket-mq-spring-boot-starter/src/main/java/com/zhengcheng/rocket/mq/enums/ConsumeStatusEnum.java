package com.zhengcheng.rocket.mq.enums;

import lombok.Getter;

/**
 * 消费状态枚举
 *
 * @author quansheng1.zhang
 * @since 2020/12/8 20:51
 */
@Getter
public enum ConsumeStatusEnum {
    /**
     * 消费中
     */
    CONSUMING("CONSUMING", "消费中"),
    /**
     * 已消费
     */
    CONSUMED("CONSUMED", "已消费");

    private String value;

    private String desc;

    ConsumeStatusEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
