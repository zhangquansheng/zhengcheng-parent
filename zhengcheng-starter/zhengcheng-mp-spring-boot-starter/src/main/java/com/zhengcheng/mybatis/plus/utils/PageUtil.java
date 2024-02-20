package com.zhengcheng.mybatis.plus.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengcheng.common.web.PageCommand;

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

}
