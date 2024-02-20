package com.zhengcheng.common.web;

import lombok.Data;

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

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private String sortField;

    private boolean asc = true;

}
