package com.zhengcheng.mybatis.plus.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengcheng.common.domain.PageCommand;
import com.zhengcheng.common.domain.PageInfo;

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
     * @param pageCommand 分页参数
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page;
     */
    public static Page getPage(PageCommand pageCommand) {
        return new Page<>(pageCommand.getPageNo(), pageCommand.getPageSize());
    }

    public static <T> PageInfo<T> getPageInfo(IPage page, Class<T> targetType) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPageNo(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setRecords(BeanUtil.copyToList(page.getRecords(), targetType));
        return pageInfo;
    }
}
