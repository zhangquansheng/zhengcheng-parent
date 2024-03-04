package com.zhengcheng.common.domain;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询实体类
 *
 * @author :    zhangquansheng
 * @date :    2020/5/18 14:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向
     */
    public static final String IS_ASC = "isAsc";


    /**
     * 当前页数
     */
    private Long pageNum;

    /**
     * 分页大小
     */
    private Long pageSize;

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
    public static final long DEFAULT_PAGE_NUM = 1L;

    /**
     * 每页显示记录数 默认值 10
     */
    public static final long DEFAULT_PAGE_SIZE = 10L;

    public static final String COMMA = ",";

    public PageQuery(Long pageNum, Long pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
