package com.zhengcheng.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 当前登录用户
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/26 1:48
 */
@Data
public class CurrentUserDto implements Serializable {
    private static final long serialVersionUID = -4968088026487026460L;
    /**
     * t_user 表中的ID，表示当前登录信息ID
     */
    private Long id;
    private String username;
    private String mobile;
    private String nickname;
    /**
     * t_single的单身会员ID
     */
    private Long singleId;
    /**
     * t_admin后台管理员ID
     */
    private Long adminId;
}