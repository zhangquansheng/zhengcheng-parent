package com.zhengcheng.common.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 分页请求参数
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/30 1:51
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = -2370551336156689540L;

    private Long pageNo = 1L;

    private Long pageSize = 10L;

    private String sortField;

    private boolean asc = true;

}
