package com.zhengcheng.satoken.utils;

import com.zhengcheng.satoken.domain.LoginUser;

import java.util.ArrayList;
import java.util.List;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;

/**
 * 基于 sa-token 的用户权限认证工具类
 *
 * @author quansheng1.zhang
 * @since 2024/3/8 15:33
 */
public class SaTokenUtil {

    private SaTokenUtil() {
    }

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
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    private static final String ROLE_SESSION_ID_PREFIX = "zhengcheng-user-role-";

    /**
     * 给角色设置权限
     *
     * @param roleId 角色ID
     * @param perms  权限
     */
    public static void setPermsByRoleId(Object roleId, List<String> perms) {
        if (CollUtil.isEmpty(perms)) {
            return;
        }
        SaSession roleSession = SaSessionCustomUtil.getSessionById(ROLE_SESSION_ID_PREFIX + roleId);
        roleSession.set(SaSession.PERMISSION_LIST, perms);
    }

    /**
     * 返回角色ID权限集合
     *
     * @param roleIds 角色ID
     * @return 权限集合
     */
    public static List<String> getPermsByRoleIds(List<Object> roleIds) {
        // 现在假设如下业务场景：我们系统中有十万个账号属于同一个角色，当我们变动这个角色的权限时，难道我们要同时清除这十万个账号的缓存信息吗？ 这显然是一个不合理的操作，同一时间缓存大量清除容易引起Redis的缓存雪崩

        // 1. 声明权限码集合
        List<String> permissionList = new ArrayList<>();
        if (CollUtil.isEmpty(roleIds)) {
            return permissionList;
        }
        // 2. 遍历角色列表，查询拥有的权限码
        for (Object roleId : roleIds) {
            SaSession roleSession = SaSessionCustomUtil.getSessionById(ROLE_SESSION_ID_PREFIX + roleId);
            List<String> perms = (List<String>) roleSession.get(SaSession.PERMISSION_LIST);
            if (CollUtil.isNotEmpty(perms)) {
                permissionList.addAll(perms);
            }
        }

        // 3. 返回权限码集合
        return permissionList;
    }
}
