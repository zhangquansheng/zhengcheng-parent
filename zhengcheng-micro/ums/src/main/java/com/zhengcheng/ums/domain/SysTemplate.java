package com.zhengcheng.ums.domain;


import com.zhengcheng.mybatis.plus.model.BaseEntity;

import java.util.List;

import lombok.Data;

/**
 * 权限模板对象 sys_template
 *
 * @author ruoyi
 * @date 2020-07-09
 */
@Data
public class SysTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 模板名称
     */
    private String name;

    private List<Integer> menuIds;

}
