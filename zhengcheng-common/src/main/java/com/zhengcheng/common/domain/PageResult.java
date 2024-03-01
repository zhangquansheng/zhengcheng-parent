package com.zhengcheng.common.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页信息
 *
 * @author :    zhangquansheng
 * @date :    2020/5/18 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -187196414152248242L;
    /**
     * 页码，从1开始
     */
    private Long pageNum;
    /**
     * 页面大小
     */
    private Long pageSize;
    /**
     * 总数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> records;

    public static <T> PageResult<T> empty(PageQuery pageQuery) {
        return new PageResult<>(pageQuery.getPageNum(), pageQuery.getPageSize(), 0L, null);
    }
}
