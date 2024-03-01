package com.zhengcheng.mybatis.plus.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengcheng.common.domain.PageQuery;
import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.exception.BizException;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;

import static com.zhengcheng.common.domain.PageQuery.COMMA;

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
    public static <T> Page<T> buildPage(PageQuery pageQuery) {
        Page<T> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<OrderItem> orderItems = buildOrderItem(pageQuery);
        if (CollUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }
        return page;
    }

    /**
     * 构建排序
     * <p>
     * 支持的用法如下:
     * {isAsc:"asc",orderByColumn:"id"} order by id asc
     * {isAsc:"asc",orderByColumn:"id,createTime"} order by id asc,create_time asc
     * {isAsc:"desc",orderByColumn:"id,createTime"} order by id desc,create_time desc
     * {isAsc:"asc,desc",orderByColumn:"id,createTime"} order by id asc,create_time desc
     */
    private static List<OrderItem> buildOrderItem(PageQuery pageQuery) {
        if (StringUtils.isBlank(pageQuery.getOrderByColumn()) || StringUtils.isBlank(pageQuery.getIsAsc())) {
            return Collections.emptyList();
        }
        String orderBy = SqlUtil.escapeOrderBySql(pageQuery.getOrderByColumn());
        orderBy = CharSequenceUtil.toUnderlineCase(orderBy);

        // 兼容前端排序类型
        String isAsc = StringUtils.replaceEach(pageQuery.getIsAsc(), new String[]{"ascending", "descending"}, new String[]{"asc", "desc"});

        String[] orderByArr = orderBy.split(COMMA);
        String[] isAscArr = isAsc.split(COMMA);
        if (isAscArr.length != 1 && isAscArr.length != orderByArr.length) {
            throw new BizException("排序参数有误");
        }

        List<OrderItem> list = new ArrayList<>();
        // 每个字段各自排序
        for (int i = 0; i < orderByArr.length; i++) {
            String orderByStr = orderByArr[i];
            String isAscStr = isAscArr.length == 1 ? isAscArr[0] : isAscArr[i];
            if ("asc".equals(isAscStr)) {
                list.add(OrderItem.asc(orderByStr));
            } else if ("desc".equals(isAscStr)) {
                list.add(OrderItem.desc(orderByStr));
            } else {
                throw new BizException("排序参数有误");
            }
        }
        return list;
    }

    public static <T> PageResult<T> buildPageResult(IPage page) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }

    public static <T> PageResult<T> buildPageResult(IPage page, Class<T> targetType) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setRecords(BeanUtil.copyToList(page.getRecords(), targetType));
        return pageResult;
    }
}
