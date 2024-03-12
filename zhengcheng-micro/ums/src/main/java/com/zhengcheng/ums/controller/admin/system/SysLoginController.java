package com.zhengcheng.ums.controller.admin.system;


import com.zhengcheng.common.domain.Result;
import com.zhengcheng.satoken.utils.SaTokenUtil;
import com.zhengcheng.ums.domain.entity.SysMenuEntity;
import com.zhengcheng.ums.domain.entity.SysUserEntity;
import com.zhengcheng.ums.domain.model.LoginBody;
import com.zhengcheng.ums.service.SysLoginService;
import com.zhengcheng.ums.service.SysMenuService;
import com.zhengcheng.ums.service.SysPermissionService;
import com.zhengcheng.ums.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.dev33.satoken.stp.StpUtil;

@RestController
@RequestMapping
public class SysLoginController {

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping(value = "/login", name = "登录方法")
    public Result login(@RequestBody LoginBody loginBody) {
        Result r = Result.ok();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        r.put(StpUtil.getTokenName(), token);
        return r;
    }

    /**
     * 登出方法
     */
    @PostMapping(value = "/logout", name = "登出方法")
    public Result logout() {
        StpUtil.logout();
        return Result.ok();
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping(value = "getInfo", name = "获取用户信息")
    public Result getInfo() {
        SysUserEntity user = sysUserService.selectUserById(SaTokenUtil.getUserId());
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        Result ajax = Result.ok();
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("userName", user.getUserName());
        ajax.put("user", userInfo);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping(value = "getRouters", name = "获取路由信息")
    public Result getRouters() {
        List<SysMenuEntity> menus = menuService.selectMenuTreeByUserId(SaTokenUtil.getUserId());
        return Result.ok(menuService.buildMenus(menus));
    }

}
