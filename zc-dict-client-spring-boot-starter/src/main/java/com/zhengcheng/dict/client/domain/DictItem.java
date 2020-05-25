package com.zhengcheng.dict.client.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据字典
 *
 * @author :    zhangquansheng
 * @date :    2020/5/25 14:32
 */
@Data
public class DictItem implements Serializable {

    private static final long serialVersionUID = -8281078526078012435L;

    private Long id;

    /**
     * 类型
     */
    private String type;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer rank;
}
