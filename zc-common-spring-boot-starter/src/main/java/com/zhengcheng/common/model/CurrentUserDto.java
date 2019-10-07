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
    /**
     * t_user id
     */
    private Long userId;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * t_single id
     */
    private Long singleId;
    /**
     * t_user_auth open_id
     */
    private String openId;
    /**
     * t_admin id
     */
    private Long adminId;
}