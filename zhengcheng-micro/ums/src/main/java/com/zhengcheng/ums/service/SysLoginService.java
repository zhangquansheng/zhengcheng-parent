package com.zhengcheng.ums.service;

import com.zhengcheng.satoken.domain.LoginUser;
import com.zhengcheng.satoken.utils.SaTokenUtil;
import com.zhengcheng.ums.common.enums.UserStatusEnum;
import com.zhengcheng.ums.domain.entity.SysUserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录校验方法
 */
@Slf4j
@Component
public class SysLoginService {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        SysUserEntity user = userService.selectUserByUserName(username);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
//            throw new UserPasswordNotMatchException();
        } else if (UserStatusEnum.DELETED.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被删除.", username);
//            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
//            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        // 验证密码，密码错误的的，需要锁定账号的逻辑 SysPasswordService
        StpUtil.login(user.getUserId());

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());

        loginUser.setRoles(sysRoleService.selectRolePermissionByUserId(user.getUserId()));
        SaTokenUtil.setLoginUser(loginUser);

//        SaTokenUtil.setPermsByRoleId();
        recordLoginInfo(user.getUserId());
        return StpUtil.getTokenValue();
    }


    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setUserId(userId);
//        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtil.date());
        userService.updateUserProfile(sysUser);
    }
}
