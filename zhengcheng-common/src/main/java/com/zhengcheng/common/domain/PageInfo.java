package com.zhengcheng.common.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页信息
 *
 * @author :    uansheng.zhang
 * @date :    2019/9/30 1:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = -187196414152248242L;
    /**
     * 页码，从1开始
     */
    private Integer pageNo;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> records;

    public static <T> PageInfo<T> empty(PageCommand pageCommand) {
        return new PageInfo<>(pageCommand.getPageNo(), pageCommand.getPageSize(), 0L, null);
    }
}
