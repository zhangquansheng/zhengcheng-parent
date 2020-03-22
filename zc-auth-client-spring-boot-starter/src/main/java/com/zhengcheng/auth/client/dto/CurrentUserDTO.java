package com.zhengcheng.auth.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 当前登录用户
 *
 * @author :    quansheng.zhang
 * @date :    2019/3/26 1:48
 */
@Data
public class CurrentUserDTO implements Serializable {
    private static final long serialVersionUID = 2213662667626687174L;
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
     * 用户名
     */
    private String username;
}