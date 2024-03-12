package com.zhengcheng.satoken.impl;


import com.google.common.collect.Lists;

import com.zhengcheng.satoken.domain.LoginUser;
import com.zhengcheng.satoken.utils.SaTokenUtil;

import java.util.Collections;
import java.util.List;

import cn.dev33.satoken.stp.StpInterface;

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
        return SaTokenUtil.getPermsByRoleIds(Collections.singletonList(getRoleList(loginId, loginType)));
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
