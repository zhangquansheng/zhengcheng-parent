package com.zhengcheng.satoken.utils;

import com.zhengcheng.satoken.domain.LoginUser;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;

/**
 * 基于 sa-token 的用户权限认证工具类
 *
 * @author quansheng1.zhang
 * @since 2024/3/8 15:33
 */
public class SaTokenUtil {

    /**
     * 用户ID
     **/
    public static Long getUserId() {
        return Long.parseLong(String.valueOf(StpUtil.getLoginId()));
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        SaSession saSession = StpUtil.getSessionByLoginId(StpUtil.getLoginId());
        return (LoginUser) saSession.get(SaSession.USER);
    }

    /**
     * 设置登陆用户
     *
     * @param loginUser 登陆用户信息
     */
    public static void setLoginUser(LoginUser loginUser) {
        SaSession saSession = StpUtil.getSessionByLoginId(StpUtil.getLoginId());
        saSession.set(SaSession.USER, loginUser);
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 是否登录，true登录
     *
     * @return
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }
}
