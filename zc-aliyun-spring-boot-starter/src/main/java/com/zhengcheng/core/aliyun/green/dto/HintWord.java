package com.zhengcheng.core.aliyun.green.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 文本命中
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 21:14
 */
@Data
public class HintWord implements Serializable {
    /**
     * 文本命中的系统关键词内容
     */
    private String context;
}
