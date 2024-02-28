package com.zhengcheng.common.domain;

import cn.hutool.core.util.IdUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息基础类
 *
 * @author :    zhangquansheng
 * @date :    2020/5/18 14:23
 */
@Data
public class BaseMessage implements Serializable {

    private static final long serialVersionUID = 607162988032310122L;
    /**
     * 消息ID，区别于消息中间的messageId
     */
    private String dataId;
    /**
     * 消息只在一定的时间范围内是有意义的，不可能收到一条一年前的消息还接着处理。
     * <p>
     * 所以根据业务需求给消息ID设置一个TTL(基于当前消息的时间戳 timestamp)
     */
    private Long timestamp;

    public BaseMessage() {
        this.dataId = IdUtil.fastSimpleUUID();
        this.timestamp = System.currentTimeMillis();
    }

}
