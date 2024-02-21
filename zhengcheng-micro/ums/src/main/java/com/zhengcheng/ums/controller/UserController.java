package com.zhengcheng.ums.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.holder.ZcUserContextHolder;
import com.zhengcheng.common.validation.annotation.Update;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.ums.common.constants.UmsConstant;
import com.zhengcheng.ums.controller.facade.OauthFacade;
import com.zhengcheng.ums.controller.facade.UserFacade;
import com.zhengcheng.ums.dto.MenuDTO;
import com.zhengcheng.ums.dto.TokenInfoDTO;
import com.zhengcheng.ums.dto.UserDTO;
import com.zhengcheng.ums.dto.command.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
    private UserFacade userFacade;
    @Autowired
    private OauthFacade oauthFacade;

    @ApiOperation("分页查询")
    @SaCheckPermission("sys:user:main")
    @PostMapping("/page")
    public Result<PageInfo<UserDTO>> page(@Valid @RequestBody UserPageCommand userPageCommand) {
        return Result.successData(userFacade.page(userPageCommand));
    }

    @ApiOperation("用户后台管理菜单")
    @GetMapping("/menu")
    public List<MenuDTO> menu() {
        return userFacade.menu(ZcUserContextHolder.getUserId());
    }

    @ApiOperation("根据用户名查询用户基本信息")
    @GetMapping("/findByUsername")
    public Result<UserDTO> findByUsername(@RequestParam("username") String username) {
        return Result.successData(userFacade.findByUsername(username));
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register")
    public Result<Long> register(String username, String password, String nickname) {
        UserCommand userCommand = new UserCommand();
        userCommand.setUsername(username);
        userCommand.setPassword(password);
        userCommand.setName(nickname);
        userCommand.setSource("userReg");
        return Result.successData(userFacade.add(userCommand));
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public Result<Long> add(@Validated @RequestBody UserCommand userCommand) {
        return Result.successData(userFacade.add(userCommand));
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public Result<Void> update(@Validated(value = Update.class) @RequestBody UserCommand userCommand) {
        userFacade.update(userCommand);
        return Result.success();
    }

    @ApiOperation("启用/禁用用户")
    @SaCheckPermission("sys:user:update")
    @PostMapping("/enable")
    public Result<Void> enable(@Valid @RequestBody EnableCommand enableCommand) {
        userFacade.enable(enableCommand);
        return Result.success();
    }

    @ApiOperation("检查用户名是否被禁用或已存在")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", dataType = "string", paramType = "query")})
    @GetMapping(value = "/checkUsername")
    public Result<Boolean> checkUsername(String username) {
        if (StrUtil.isEmpty(username)) {
            return Result.successData(false);
        }

        return Result.successData(!userFacade.usernameExists(username));
    }

    //    @Idempotent(keys = "#userLoginCommand.username", location = "login", delKey = true)
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<TokenInfoDTO> login(@RequestBody UserLoginCommand userLoginCommand, HttpServletRequest request) {
        SaTokenInfo saTokenInfo = oauthFacade.login(userLoginCommand, request);
        return Result.successData(BeanUtil.copyProperties(saTokenInfo, TokenInfoDTO.class));
    }

    @ApiOperation(value = "发送登录手机验证码")
    @PostMapping("/sendLoginSms")
    public Result<Void> sendLoginSms(@RequestBody SendSmsCommand sendSmsCommand) throws UnsupportedEncodingException {
        oauthFacade.sendLoginSms(sendSmsCommand);
        return Result.success();
    }

    @ApiOperation(value = "用户手机登录")
    @PostMapping("/mobile/login")
    public Result<TokenInfoDTO> mobileLogin(@RequestBody UserMobileLoginCommand userMobileLoginCommand, HttpServletRequest request) {
        SaTokenInfo saTokenInfo = oauthFacade.login(userMobileLoginCommand, request);
        return Result.successData(BeanUtil.copyProperties(saTokenInfo, TokenInfoDTO.class));
    }

    @ApiOperation("根据token获取用户基本信息")
    @GetMapping("/info")
    public Result<UserDTO> info(@RequestParam(UmsConstant.TOKEN_NAME) String token) {
        return Result.successData(userFacade.findByByToken(token));
    }

    @ApiOperation(value = "用户注销登录")
    @PostMapping(value = "/logout")
    public Result<Void> logout(@RequestHeader(UmsConstant.TOKEN_NAME) String token) {
        StpUtil.logoutByTokenValue(token);
        return Result.success();
    }

}
