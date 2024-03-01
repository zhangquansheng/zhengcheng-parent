package com.zhengcheng.mybatis.plus.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengcheng.common.domain.PageQuery;
import com.zhengcheng.common.domain.PageResult;

import cn.hutool.core.bean.BeanUtil;

/**
 * 分页工具类
 *
 * @author quansheng1.zhang
 * @since 2020/11/9 19:41
 */
public class PageUtil {

    /**
     * 获取 Mybatis-Plus分页查询参数
     *
     * @param pageQuery 分页参数
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page;
     */
    public static Page getPage(PageQuery pageQuery) {
        return new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
    }

    public static <T> PageResult<T> getPageResult(IPage page, Class<T> targetType) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setPageNo(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setRecords(BeanUtil.copyToList(page.getRecords(), targetType));
        return pageResult;
    }
}
