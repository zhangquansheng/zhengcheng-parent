package com.zhengcheng.satoken.holder;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.zhengcheng.common.domain.UserInfo;

import cn.hutool.core.util.StrUtil;

/**
 * 新征程框架，用户上下文 ThreadLocal
 *
 * @author :    zhngquansheng
 * @date :    2019/12/20 15:17
 */
public class ZcUserContextHolder {

    public ZcUserContextHolder() {
    }

    private static final TransmittableThreadLocal<UserInfo> userInfoLocal = new TransmittableThreadLocal<>();

    public static UserInfo getUserInfo() {
        return userInfoLocal.get();
    }

    public static void setUserInfo(UserInfo userInfo) {
        userInfoLocal.set(userInfo);
    }

    public static void removeUserInfo() {
        userInfoLocal.remove();
    }

    public static Long getUserId() {
        UserInfo userInfo = getUserInfo();
        return userInfo == null ? null : userInfo.getId();
    }

    public static String getUsername() {
        UserInfo userInfo = getUserInfo();
        return userInfo == null ? null : userInfo.getUsername();
    }

    public static String getUserNo() {
        UserInfo userInfo = getUserInfo();
        return userInfo == null ? null : userInfo.getUserNo();
    }

    public static String getOperator() {
        UserInfo userInfo = getUserInfo();
        return userInfo == null ? "" : StrUtil.format("{}({})", userInfo.getUsername(), userInfo.getUserNo());
    }

}
