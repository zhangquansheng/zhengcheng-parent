package com.zhengcheng.magic.controller.facade.internal;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.magic.common.utils.IpAddressUtil;
import com.zhengcheng.magic.controller.facade.IOauthFacade;
import com.zhengcheng.magic.domain.entity.User;
import com.zhengcheng.magic.domain.entity.UserLoginLog;
import com.zhengcheng.magic.domain.enums.LoginResultEnum;
import com.zhengcheng.magic.domain.enums.LoginTypeEnum;
import com.zhengcheng.magic.service.IUserLoginLogService;
import com.zhengcheng.magic.service.IUserService;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * UserFacadeImpl
 *
 * @author quansheng1.zhang
 * @since 2021/7/15 14:28
 */
@Slf4j
@Service
public class OauthFacadeImpl implements IOauthFacade {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserLoginLogService userLoginLogService;

    @Override
    public SaTokenInfo login(String username, String enPassword, HttpServletRequest request) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        // TODO 一、确认客户端加密规则
        return userLogin(user, enPassword, request);
    }

    private SaTokenInfo userLogin(User user, String password, HttpServletRequest request) {
        if (Objects.isNull(user)) {
            throw new BizException("用户名或密码错误！");
        }

        if (!userService.isSamePassword(password, user.getPassword())) {
            throw new BizException("用户名或密码错误！");
        }

        if (user.isDisabled()) {
            userLoginLogService.save(UserLoginLog.builder().userId(user.getId()).type(LoginTypeEnum.LOGIN)
                .serverIp(NetUtil.getLocalhostStr()).loginIp(IpAddressUtil.getIpAddress(request))
                .result(LoginResultEnum.FAILURE).content("用户已被禁用！").build());
            throw new BizException("用户已被禁用！");
        }

        StpUtil.login(user.getId());
        userLoginLogService.save(
            UserLoginLog.builder().userId(user.getId()).type(LoginTypeEnum.LOGIN).serverIp(NetUtil.getLocalhostStr())
                .loginIp(IpAddressUtil.getIpAddress(request)).result(LoginResultEnum.SUCCESS).build());
        return StpUtil.getTokenInfo();
    }
}
