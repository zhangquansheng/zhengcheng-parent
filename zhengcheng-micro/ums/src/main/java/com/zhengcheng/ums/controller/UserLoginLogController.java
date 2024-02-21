package com.zhengcheng.ums.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhengcheng.common.web.PageCommand;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.ums.controller.facade.UserLoginLogFacade;
import com.zhengcheng.ums.dto.UserLoginLogDTO;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * UserLoginLogController
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 21:23
 */
@Api(tags = { "登录日志管理" })
@RestController
@RequestMapping("/admin/userLoginLog")
public class UserLoginLogController {

    @Autowired
    private UserLoginLogFacade userLoginLogFacade;

    @ApiOperation("分页查询")
    @SaCheckPermission("sys:log:main")
    @PostMapping("/page")
    public Result<PageInfo<UserLoginLogDTO>> page(@Valid @RequestBody PageCommand pageCommand) {
        return Result.successData(userLoginLogFacade.page(pageCommand));
    }
}
