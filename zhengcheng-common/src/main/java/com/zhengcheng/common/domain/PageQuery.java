package com.zhengcheng.common.domain;


import java.io.Serializable;

import lombok.Data;

/**
 * 分页查询实体类
 *
 * @author :    zhangquansheng
 * @date :    2020/5/18 14:23
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前记录起始索引
     */
    private static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    private static final String PAGE_SIZE = "pageSize";

    /**
     * 分页大小
     */
    private Long pageSize;

    /**
     * 当前页数
     */
    private Long pageNum;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc;

    /**
     * 当前记录起始索引 默认值
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 每页显示记录数 默认值 10
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String COMMA = ",";

}
