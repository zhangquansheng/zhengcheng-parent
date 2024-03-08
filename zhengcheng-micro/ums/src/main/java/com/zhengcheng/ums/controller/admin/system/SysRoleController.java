package com.zhengcheng.ums.controller.admin.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.domain.entity.SysRoleEntity;
import com.zhengcheng.ums.domain.entity.SysUserEntity;
import com.zhengcheng.ums.domain.entity.SysUserRoleEntity;
import com.zhengcheng.ums.service.SysPermissionService;
import com.zhengcheng.ums.service.SysRoleService;
import com.zhengcheng.ums.service.SysUserService;

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

@RestController
@RequestMapping("/system/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPermissionService permissionService;
//    @Autowired
//    private TokenService tokenService;

    @GetMapping("/list")
    public Result list(SysRoleEntity role) {
        PageResult<SysRoleEntity> list = roleService.page(role);
        return Result.ok().put(list);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @GetMapping(value = "/{roleId}")
    public Result getInfo(@PathVariable Long roleId) {
        return Result.ok(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @PostMapping
    public Result add(@Validated @RequestBody SysRoleEntity role) {
        if (!roleService.checkRoleNameUnique(role)) {
            return Result.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return Result.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return Result.ok(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @PutMapping
    public Result edit(@Validated @RequestBody SysRoleEntity role) {
        roleService.checkRoleAllowed(role);
        if (!roleService.checkRoleNameUnique(role)) {
            return Result.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return Result.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }

//        if (roleService.updateRole(role) > 0) {
//            // 更新缓存用户权限
//            LoginUser loginUser = SecurityUtils.getLoginUser();
//            if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
//                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
//                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
//                tokenService.setLoginUser(loginUser);
//            }
//            permissionService.resetLoginUserRoleCache(role.getRoleId());
//            return Result.ok();
//        }
        return Result.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody SysRoleEntity role) {
        roleService.checkRoleAllowed(role);
        roleService.updateRoleStatus(role);
        //更新redis缓存权限数据
//        permissionService.resetLoginUserRoleCache(role.getRoleId());
        return Result.ok();
    }

    /**
     * 删除角色
     */
//    @PreAuthorize("@ss.hasPermi('system:role:remove')")
    @DeleteMapping("/{roleIds}")
    public Result remove(@PathVariable Long[] roleIds) {
        roleService.deleteRoleByIds(roleIds);
        //更新redis缓存权限数据
//        Arrays.stream(roleIds).forEach(id -> permissionService.resetLoginUserRoleCache(id));

        return Result.ok();
    }


    /**
     * 查询已分配用户角色列表
     */
    @GetMapping("/authUser/allocatedList")
    public Result allocatedList(SysUserEntity user) {
        Page<SysUserEntity> page = userService.selectAllocatedList(user);
        return Result.ok().put(page);
    }

    /**
     * 查询未分配用户角色列表
     */
    @GetMapping("/authUser/unallocatedList")
    public Result unallocatedList(SysUserEntity user) {
        Page<SysUserEntity> page = userService.selectUnallocatedList(user);
        return Result.ok().put(page);
    }

    /**
     * 取消授权用户
     */
    @PutMapping("/authUser/cancel")
    public Result cancelAuthUser(@RequestBody SysUserRoleEntity userRole) {
        int i = roleService.deleteAuthUser(userRole);
        //更新redis缓存权限数据
//        permissionService.resetLoginUserRoleCache(userRole.getRoleId());
        return Result.ok(i);
    }

    /**
     * 批量取消授权用户
     */
//    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @PutMapping("/authUser/cancelAll")
    public Result cancelAuthUserAll(Long roleId, Long[] userIds) {
        int i = roleService.deleteAuthUsers(roleId, userIds);
        //更新redis缓存权限数据
//        permissionService.resetLoginUserRoleCache(roleId);

        return Result.ok(i);
    }

    /**
     * 批量选择用户授权
     */
//    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @PutMapping("/authUser/selectAll")
    public Result selectAuthUserAll(Long roleId, Long[] userIds) {
        return Result.ok(roleService.insertAuthUsers(roleId, userIds));
    }

}
