package com.zhengcheng.common.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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

    /**
     * 设置 分页信息
     *
     * @param page IPage
     */
    public static PageInfo setPage(IPage page, List list) {
        PageInfo pageInfo = new PageInfo<>();
        pageInfo.setPageNo(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return pageInfo;
    }
}
