package com.zhengcheng.magic.controller;

import com.zhengcheng.common.validation.annotation.Insert;
import com.zhengcheng.common.validation.annotation.Update;
import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.common.utils.Result;
import com.zhengcheng.magic.controller.command.RoleAuthorityCommand;
import com.zhengcheng.magic.controller.command.RoleCommand;
import com.zhengcheng.magic.controller.facade.IRoleFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.RoleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色表(Role)控制层
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@Api(tags = {"角色表(Role)接口"})
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleFacade roleFacade;

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/{id}")
    public Result<RoleDTO> findById(@PathVariable("id") Long id) {
        return Result.successData(roleFacade.findById(id));
    }

    @ApiOperation("添加单条数据")
    @PostMapping("/add")
    public Result<Long> add(
            @Validated({Insert.class}) @RequestBody RoleCommand roleCommand) {
        return Result.successData(roleFacade.add(roleCommand));
    }

    @ApiOperation("更新单条数据")
    @PostMapping("/update")
    public Result<Long> update(
            @Validated({Update.class}) @RequestBody RoleCommand roleCommand) {
        return Result.successData(roleFacade.update(roleCommand));
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public Result<PageInfo<RoleDTO>> page(@Valid @RequestBody PageCommand pageCommand) {
        return Result.successData(roleFacade.page(pageCommand));
    }

    @ApiOperation("编辑角色权限")
    @PostMapping("/authority")
    public Result<Void> authority(@Validated({Insert.class}) @RequestBody RoleAuthorityCommand roleAuthorityCommand) {
        roleFacade.addRoleAuthority(roleAuthorityCommand);
        return Result.success();
    }

}
