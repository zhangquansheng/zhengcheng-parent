package com.zhengcheng.auth.client.constant;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Security 权限常量
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/24 0:06
 */
public interface SecurityConstants {
    /**
     * open_id 自定义授权登录方式
     */
    String OPEN_ID_GRANT_TYPE = "open_id";

    /**
     * 用户信息分隔符
     */
    String USER_SPLIT = ":";

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * 应用信息头
     */
    String CLIENT_HEADER = "x-client-header";

    /**
     * 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 密码模式
     */
    String PASSWORD = "password";

    /**
     * 刷新token
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 登录失败提示
     */
    String LOGIN_FAIL_MESSAGE = "用户名不存在或者密码错误";
    /**
     * 授权登录失败提示
     */
    String AUTH_FAIL_MESSAGE = "授权信息错误";

    /**
     * 默认不需要授权的URL
     */
    List<String> DEFAULT_PERMIT_ALL = Lists.newArrayList(
            "/favicon.ico",
            "/oauth/**",
            "/webjars/**",
            "/resources/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs");
}