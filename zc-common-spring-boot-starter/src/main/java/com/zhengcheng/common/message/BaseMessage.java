package com.zhengcheng.common.message;

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
     * 消息ID（作用:防止重复消费）
     */
    private String dataId;
    /**
     * 时间戳 秒(s) ,例如：1589783088
     */
    private long timestamp;

    public BaseMessage() {
        this.dataId = IdUtil.fastSimpleUUID();
        this.timestamp = System.currentTimeMillis() / 1000;
    }

}
