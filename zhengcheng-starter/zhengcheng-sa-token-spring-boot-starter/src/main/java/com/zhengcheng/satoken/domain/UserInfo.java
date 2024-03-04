package com.zhengcheng.satoken.domain;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -87464380427076695L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户编号
     */
    private String userNo;
    /**
     * 用户token
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
}
