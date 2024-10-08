package com.zhengcheng.ums.controller.admin.system;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.satoken.utils.SaTokenUtil;
import com.zhengcheng.ums.domain.entity.SysRoleEntity;
import com.zhengcheng.ums.domain.entity.SysUserEntity;
import com.zhengcheng.ums.service.SysPasswordService;
import com.zhengcheng.ums.service.SysRoleService;
import com.zhengcheng.ums.service.SysUserService;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysPasswordService passwordService;

    /**
     * 分页
     */
    @GetMapping("list")
    @SaCheckPermission("system:user:list")
    public Result page(SysUserEntity sysUserEntity) {
        PageResult<SysUserEntity> page = userService.page(sysUserEntity);
        return Result.ok().put(page);
    }

    /**
     * 信息
     */
    @SaCheckPermission("system:user:query")
    @GetMapping({"{userId}", "/"})
    public Result getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        Result res = Result.ok();
        List<SysRoleEntity> roles = roleService.selectRoleAll();
        res.put("roles", SysUserEntity.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        if (ObjectUtil.isNotNull(userId)) {
            SysUserEntity sysUser = userService.selectUserById(userId);
            res.put("data", sysUser);
            res.put("roleIds", sysUser.getRoles().stream().map(SysRoleEntity::getRoleId).filter(Objects::nonNull).collect(Collectors.toList()));
        }

        return res;
    }

    /**
     * 新增用户
     */
    @SaCheckPermission("system:user:add")
    @PostMapping
    public Result add(@Validated @RequestBody SysUserEntity sysUserEntity) {
        userService.insertUser(sysUserEntity);
        return Result.ok();
    }

    /**
     * 修改
     */
    @SaCheckPermission("system:user:edit")
    @PutMapping
    public Result update(@Validated @RequestBody SysUserEntity user) {
        userService.checkUserAllowed(user);
        if (!(userService.checkUserNameUnique(user))) {
            return Result.error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !(userService.checkPhoneUnique(user))) {
            return Result.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !(userService.checkEmailUnique(user))) {
            return Result.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setPassword(null);

        return Result.ok(userService.updateUser(user));
    }

    /**
     * 删除
     */
    @SaCheckPermission("system:user:remove")
    @DeleteMapping("/{userIds}")
    public Result remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, SaTokenUtil.getUserId())) {
            return Result.error("当前用户不能删除");
        }
        return Result.ok(userService.deleteUserByIds(userIds));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/authRole/{userId}")
    public Result authRole(@PathVariable("userId") Long userId) {
        Result res = Result.ok();
        SysUserEntity user = userService.selectUserById(userId);
        List<SysRoleEntity> roles = roleService.selectRolesByUserId(userId);
        res.put("user", user);
        res.put("roles", SysUserEntity.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return res;
    }

    /**
     * 用户授权角色
     */
    @SaCheckPermission("system:user:edit")
    @PutMapping("/authRole")
    public Result insertAuthRole(Long userId, Long[] roleIds) {
        if (!SysUserEntity.isAdmin(userId)) {
            userService.insertUserAuth(userId, roleIds);
            return Result.ok();
        } else {
            return Result.error("不可操作超级管理员");
        }

    }

    /**
     * 重置密码
     */
    @SaCheckPermission("system:user:resetPwd")
    @PutMapping("/resetPwd")
    public Result resetPwd(@RequestBody SysUserEntity user) {
        userService.checkUserAllowed(user);
        user.setPassword(passwordService.encode(user.getPassword()));
        return Result.ok(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:user:edit")
    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody SysUserEntity user) {
        userService.checkUserAllowed(user);
        userService.updateUserStatus(user);
        //将指定账号踢下线
        StpUtil.kickout(user.getUserId());
        return Result.ok();
    }


}
