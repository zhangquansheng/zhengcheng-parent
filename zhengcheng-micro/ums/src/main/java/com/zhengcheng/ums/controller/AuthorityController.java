package com.zhengcheng.ums.controller;

import com.zhengcheng.common.utils.Result;
import com.zhengcheng.common.validation.annotation.Update;
import com.zhengcheng.ums.controller.facade.AuthorityFacade;
import com.zhengcheng.ums.dto.AuthorityDTO;
import com.zhengcheng.ums.dto.command.AuthorityCommand;
import com.zhengcheng.ums.dto.command.EnableCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AuthorityController
 *
 * @author quansheng1.zhang
 * @since 2022/4/21 9:27
 */
@Api(tags = {"权限"})
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthorityFacade authorityFacade;

    @ApiOperation("查询权限数据")
    @GetMapping("/data")
    public Result<List<AuthorityDTO>> data(@RequestParam(value = "pid", required = false) Long pid) {
        return Result.successData(authorityFacade.findByPid(pid));
    }

    @ApiOperation("保存")
    @SaCheckPermission("sys:authority:save")
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody AuthorityCommand authorityCommand) {
        authorityFacade.save(authorityCommand);
        return Result.success();
    }

    @ApiOperation("删除")
    @SaCheckPermission("sys:authority:del")
    @DeleteMapping("/operate/remove/{id}")
    public Result<Boolean> remove(@PathVariable("id") Long id) {
        return Result.successData(authorityFacade.deleteById(id));
    }

    @ApiOperation("更新")
    @SaCheckPermission("sys:authority:update")
    @PostMapping("/update")
    public Result<Long> update(@Validated(value = Update.class) @RequestBody AuthorityCommand authorityCommand) {
        authorityFacade.update(authorityCommand);
        return Result.success();
    }

    @ApiOperation("根据ID启用/禁用")
    @SaCheckPermission("sys:authority:enable")
    @PostMapping("/operate/enable")
    public Result<Boolean> enable(@Valid @RequestBody EnableCommand enableCommand) {
        return Result.successData(authorityFacade.enable(enableCommand));
    }

    @ApiOperation("根据角色ID查询权限树数据")
    @GetMapping("/findByRoleId")
    public Result<List<AuthorityDTO>> findByRoleId(@RequestParam(value = "roleId", required = false) Long roleId) {
        return Result.successData(authorityFacade.findByRoleId(roleId));
    }

}
