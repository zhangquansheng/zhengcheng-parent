package com.zhengcheng.common.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO 基础类
 *
 * @author quansheng1.zhang
 * @since 2020/11/28 10:43
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 5754586603014851711L;
    /**
     * ID
     */
    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
