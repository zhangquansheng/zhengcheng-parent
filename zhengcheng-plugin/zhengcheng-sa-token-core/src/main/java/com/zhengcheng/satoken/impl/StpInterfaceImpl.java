package com.zhengcheng.satoken.impl;


import com.google.common.collect.Lists;

import com.zhengcheng.satoken.domain.LoginUser;
import com.zhengcheng.satoken.utils.SaTokenUtil;

import java.util.ArrayList;
import java.util.List;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;

/**
 * 自定义权限验证接口扩展
 * <p>
 * http://sa-token.dev33.cn/doc/index.html#/use/jur-auth
 *
 * @author quansheng1.zhang
 * @since 2021/8/13 14:23
 */
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> roles = getRoleList(loginId, loginType);
        if (CollUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }
        // 如果是管理员，则拥有所有的权限
        if (roles.contains(SaTokenUtil.ADMIN_ROLE_KEY)) {
            return Lists.newArrayList("*:*:*");
        }

        return SaTokenUtil.getPermsByRoles(roles);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = SaTokenUtil.getLoginUser();
        return Lists.newArrayList(loginUser.getRoles());
    }
}
