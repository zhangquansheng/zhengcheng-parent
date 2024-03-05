package com.zhengcheng.ums.domain.model;

import com.zhengcheng.satoken.domain.UserInfo;
import com.zhengcheng.ums.domain.entity.SysUserEntity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser extends UserInfo {
    private static final long serialVersionUID = 1L;
    /**
     * 用户信息
     */
    private SysUserEntity user;
    /**
     * 资源列表
     */
    private Set<String> resources;

    public LoginUser(Long userId, SysUserEntity user, Set<String> permissions, Set<String> resources) {
        super.setUserId(userId);
        this.user = user;
        super.setPermissions(permissions);
        this.resources = resources;
    }
}
