package com.zhengcheng.ums.domain.model;


import com.zhengcheng.ums.domain.entity.SysUserEntity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */
@Data
@AllArgsConstructor
public class LoginUser {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户唯一标识
     */
    private String token;


    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 资源列表
     */
    private Set<String> resources;

    /**
     * 用户信息
     */
    private SysUserEntity user;

    public LoginUser(Long userId, SysUserEntity user, Set<String> permissions, Set<String> resources) {
        this.userId = userId;
        this.user = user;
        this.permissions = permissions;
        this.resources = resources;
    }

}
