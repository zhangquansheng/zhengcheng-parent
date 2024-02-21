package com.zhengcheng.ums.controller.facade;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.zhengcheng.ums.dto.command.SendSmsCommand;
import com.zhengcheng.ums.dto.command.UserLoginCommand;
import com.zhengcheng.ums.dto.command.UserMobileLoginCommand;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * IUserFacade
 *
 * @author quansheng1.zhang
 * @since 2021/7/15 14:27
 */
public interface OauthFacade {

    SaTokenInfo login(UserLoginCommand userLoginCommand, HttpServletRequest request);

    /**
     * 手机验证码登录
     */
    SaTokenInfo login(UserMobileLoginCommand userMobileLoginCommand, HttpServletRequest request);

    void sendLoginSms(SendSmsCommand sendSmsCommand) throws UnsupportedEncodingException;
}
