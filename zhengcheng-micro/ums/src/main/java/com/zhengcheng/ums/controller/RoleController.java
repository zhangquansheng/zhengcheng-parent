package com.zhengcheng.ums.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.zhengcheng.common.validation.annotation.Update;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.ums.controller.facade.RoleFacade;
import com.zhengcheng.ums.dto.command.EnableCommand;
import com.zhengcheng.ums.dto.command.RoleAuthorityCommand;
import com.zhengcheng.ums.dto.command.RoleCommand;
import com.zhengcheng.ums.dto.command.RolePageCommand;
import com.zhengcheng.ums.dto.RoleDTO;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * RoleController
 *
 * @author quansheng1.zhang
 * @since 2022/4/19 18:18
 */
@Api(tags = { "角色管理" })
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleFacade roleFacade;

    @ApiOperation("分页查询")
    @SaCheckPermission("sys:role:main")
    @PostMapping("/page")
    public Result<PageInfo<RoleDTO>> page(@Valid @RequestBody RolePageCommand rolePageCommand) {
        return Result.successData(roleFacade.page(rolePageCommand));
    }

    @ApiOperation("根据ID删除")
    @SaCheckPermission("sys:role:del")
    @DeleteMapping("/remove/{id}")
    public Result<Void> removeById(@PathVariable("id") Long id) {
        roleFacade.removeById(id);
        return Result.success();
    }

    @ApiOperation("根据ID列表批量删除")
    @SaCheckPermission("sys:role:del")
    @DeleteMapping("/batchRemove")
    public Result<Boolean> batchRemove(@RequestParam("ids") List<Long> ids) {
        return Result.successData(roleFacade.batchRemove(ids));
    }

    @ApiOperation("根据ID启用/禁用")
    @PostMapping("/enable")
    public Result<Boolean> enable(@Valid @RequestBody EnableCommand enableCommand) {
        return Result.successData(roleFacade.enable(enableCommand));
    }

    @ApiOperation("角色授权")
    @SaCheckPermission("sys:role:authority")
    @PostMapping("/saveRoleAuthority")
    public Result<Boolean> saveRoleAuthority(@Valid @RequestBody RoleAuthorityCommand roleAuthorityCommand) {
        return Result.successData(roleFacade.saveRoleAuthority(roleAuthorityCommand));
    }

    @ApiOperation("保存角色")
    @SaCheckPermission("sys:role:save")
    @PostMapping("/save")
    public Result<Long> save(@Valid @RequestBody RoleCommand roleCommand) {
        return Result.successData(roleFacade.add(roleCommand));
    }

    @ApiOperation("更新角色")
    @SaCheckPermission("sys:role:update")
    @PostMapping("/update")
    public Result<Long> update(@Validated(value = Update.class) @RequestBody RoleCommand roleCommand) {
        return Result.successData(roleFacade.update(roleCommand));
    }

}
