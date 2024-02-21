package com.zhengcheng.magic.common.config.sa.token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhengcheng.magic.service.IRoleAuthorityService;
import com.zhengcheng.magic.service.IUserRoleService;

import cn.dev33.satoken.stp.StpInterface;

/**
 * 自定义权限验证接口扩展
 * 
 * http://sa-token.dev33.cn/doc/index.html#/use/jur-auth
 *
 * @author quansheng1.zhang
 * @since 2021/8/13 14:23
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return roleAuthorityService.getAuthorityCodeList(Long.parseLong(String.valueOf(loginId)));
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return userRoleService.getRoleCodeList(Long.parseLong(String.valueOf(loginId)));
    }
}
