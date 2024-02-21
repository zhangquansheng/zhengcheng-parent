package com.zhengcheng.ums.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhengcheng.ums.service.AuthorityService;
import com.zhengcheng.ums.service.RoleService;

import cn.dev33.satoken.stp.StpInterface;

/**
 * 自定义权限验证接口扩展
 *
 * @author quansheng1.zhang
 * @since 2022/4/21 17:32
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private RoleService      roleService;
    @Autowired
    private AuthorityService authorityService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return authorityService.getPermissionList(loginId, loginType);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleService.getRoleList(loginId, loginType);
    }
}
