package com.zhengcheng.ums.service;

import com.zhengcheng.ums.controller.BaseTest;
import com.zhengcheng.ums.controller.facade.OauthFacade;
import com.zhengcheng.ums.controller.facade.UserFacade;
import com.zhengcheng.ums.dto.command.SendSmsCommand;
import com.zhengcheng.ums.dto.command.UserCommand;
import com.zhengcheng.ums.dto.command.UserMobileLoginCommand;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

/**
 * UserFacadeTest
 *
 * @author quansheng1.zhang
 * @since 2022/7/10 15:35
 */
public class UserFacadeTest extends BaseTest {

    @Autowired
    private UserFacade userFacade;
    @Autowired
    private OauthFacade oauthFacade;

    @Test
    public void mobileLogin() {
        UserMobileLoginCommand userMobileLoginCommand = new UserMobileLoginCommand();
        userMobileLoginCommand.setMobile("13916500302");
        userMobileLoginCommand.setCode("379731");
        SaTokenInfo saTokenInfo = oauthFacade.login(userMobileLoginCommand, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        System.out.printf(JSONUtil.toJsonStr(saTokenInfo));
    }

    @Test
    public void sendSms() throws UnsupportedEncodingException {
        SendSmsCommand sendSmsCommand = new SendSmsCommand();
        sendSmsCommand.setMobile("13916500302");
        oauthFacade.sendLoginSms(sendSmsCommand);
    }

    @Test
    public void add() {
        UserCommand userCommand = new UserCommand();
        userCommand.setUsername("admin-1");
        userCommand.setPassword("Toujiao33443*");
        userCommand.setEnable(Boolean.TRUE);
        userCommand.setSource("system");
        userFacade.add(userCommand);
    }

    @Test
    public void login() {

    }

    @Test
    public void safeDomain() {
        String domain = "https://test.a.com/xx/xx.jpg";
        URL url = URLUtil.url(domain);
        System.out.println(HttpUtil.isHttp(domain));
        System.out.println(HttpUtil.isHttps(domain));
        System.out.println(url.getHost());

        List<String> securityDomainList = new ArrayList<>();
        securityDomainList.add("b.com");
        securityDomainList.add("www.a.com");

        System.out.println(securityDomainList.contains(url.getHost()));
    }
}
