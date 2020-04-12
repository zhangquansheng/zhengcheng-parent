package com.zhengcheng.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author :    uansheng.zhang
 * @date :    2019/9/30 1:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -187196414152248242L;
    /**
     * 页码，从1开始
     */
    private Long pageNo;
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
    private List<T> list;

}
