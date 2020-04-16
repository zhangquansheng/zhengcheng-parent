package com.zhengcheng.common.web;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/30 1:51
 */
@Data
public class PageCommand implements Serializable {

    private static final long serialVersionUID = -2370551336156689540L;

    private Integer current = 1;

    private Integer size = 10;

    private String sortField;

    private boolean asc = true;

    public Page getPage() {
        Page page = new Page(this.current == null ? 1 : this.current, this.size == null ? 10 : this.size);
        if (Strings.isNotBlank(this.sortField)) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(this.sortField);
            orderItem.setAsc(this.asc);
            page.setOrders(Lists.newArrayList(orderItem));
        }
        return page;
    }
}
