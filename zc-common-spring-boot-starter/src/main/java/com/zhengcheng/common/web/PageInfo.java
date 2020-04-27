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
    private List<T> list;

    public static <T> PageInfo<T> create(IPage page, List<T> list) {
        return new PageInfo<>(new Long(page.getCurrent()).intValue(), new Long(page.getSize()).intValue(), page.getTotal(), list);
    }

    public static <T> PageInfo<T> create(IPage<T> page) {
        return new PageInfo<>(new Long(page.getCurrent()).intValue(), new Long(page.getSize()).intValue(), page.getTotal(), page.getRecords());
    }

    public static <T> PageInfo<T> empty(PageCommand pageCommand) {
        return new PageInfo<>(pageCommand.getCurrent(), pageCommand.getSize(), 0L, null);
    }
}
