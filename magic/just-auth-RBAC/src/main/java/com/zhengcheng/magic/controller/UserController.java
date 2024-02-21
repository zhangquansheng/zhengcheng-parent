package com.zhengcheng.magic.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zhengcheng.common.validation.annotation.Insert;
import com.zhengcheng.common.utils.Result;
import com.zhengcheng.magic.controller.command.UserCommand;
import com.zhengcheng.magic.controller.command.UserRoleCommand;
import com.zhengcheng.magic.controller.facade.IUserFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户(User)控制层
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:50
 */
@Api(tags = {"用户(User)接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserFacade userFacade;

    @ApiOperation("当前用户信息")
    @GetMapping("/current")
    public Result<UserDTO> current() {
        return Result.successData(userFacade.findCurrent(Long.parseLong(String.valueOf(StpUtil.getLoginId()))));
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public Result<Long> add(@Validated @RequestBody UserCommand userCommand) {
        return Result.successData(userFacade.add(userCommand));
    }

    @ApiOperation("编辑用户角色")
    @PostMapping("/role")
    public Result<Void> role(@Validated({Insert.class}) @RequestBody UserRoleCommand userRoleCommand) {
        userFacade.addUserRole(userRoleCommand);
        return Result.success();
    }

}
