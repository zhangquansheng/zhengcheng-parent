package com.zhengcheng.mvc.utils;

import com.zhengcheng.common.domain.PageQuery;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.convert.Convert;

import static com.zhengcheng.common.domain.PageQuery.DEFAULT_PAGE_NUM;
import static com.zhengcheng.common.domain.PageQuery.DEFAULT_PAGE_SIZE;
import static com.zhengcheng.common.domain.PageQuery.IS_ASC;
import static com.zhengcheng.common.domain.PageQuery.ORDER_BY_COLUMN;
import static com.zhengcheng.common.domain.PageQuery.PAGE_NUM;
import static com.zhengcheng.common.domain.PageQuery.PAGE_SIZE;

/**
 * PageQueryUtil
 *
 * @author quansheng1.zhang
 * @since 2024/3/1 13:30
 */
public class PageQueryUtil {

    public static PageQuery build() {
        HttpServletRequest request = getRequest();
        if (Objects.isNull(request)) {
            return new PageQuery(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
        }

        Long pageNum = Convert.toLong(request.getParameter(PAGE_NUM), DEFAULT_PAGE_NUM);
        Long pageSize = Convert.toLong(request.getParameter(PAGE_SIZE), DEFAULT_PAGE_SIZE);
        if (pageNum <= 0) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        String orderByColumn = Convert.toStr(request.getParameter(ORDER_BY_COLUMN), "");
        String isAsc = Convert.toStr(request.getParameter(IS_ASC), "");

        return new PageQuery(pageNum, pageSize, orderByColumn, isAsc);
    }

    /**
     * 获取request
     */
    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
