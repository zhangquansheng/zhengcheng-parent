package com.zhengcheng.ums.service;

import com.google.common.collect.Lists;

import com.zhengcheng.cache.redis.RedisCache;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.mvc.utils.I18nUtil;
import com.zhengcheng.mvc.utils.IpUtil;
import com.zhengcheng.satoken.domain.LoginUser;
import com.zhengcheng.satoken.utils.SaTokenUtil;
import com.zhengcheng.ums.common.constant.CacheConstants;
import com.zhengcheng.ums.common.constant.Constants;
import com.zhengcheng.ums.common.enums.UserStatusEnum;
import com.zhengcheng.ums.common.exception.user.UserPasswordNotMatchException;
import com.zhengcheng.ums.common.manager.AsyncFactory;
import com.zhengcheng.ums.common.manager.AsyncManager;
import com.zhengcheng.ums.domain.entity.SysUserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
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
    private SysMenuService sysMenuService;
    @Autowired
    private SysPasswordService sysPasswordService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RedisCache redisCache;

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
        validateCaptcha(code, uuid);

        SysUserEntity user = userService.selectUserByUserName(username);
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, I18nUtil.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        } else if (UserStatusEnum.DELETED.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被删除.", username);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, user.getUserId(), Constants.LOGIN_FAIL, "账号已被删除"));
            throw new BizException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, user.getUserId(), Constants.LOGIN_FAIL, "账号已停用"));
            throw new BizException("对不起，您的账号：" + username + " 已停用");
        }

        sysPasswordService.validate(user, username, password);

        StpUtil.login(user.getUserId());

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());

        loginUser.setRoles(sysRoleService.selectRolePermissionByUserId(user.getUserId()));
        SaTokenUtil.setLoginUser(loginUser);
        loginUser.getRoles().forEach(role -> SaTokenUtil.setPermsByRole(role, Lists.newArrayList(sysMenuService.selectMenuPermsByRoleKey(role))));

        recordLoginInfo(user.getUserId());
        //异步记录登录成功日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, loginUser.getUserId(), Constants.LOGIN_SUCCESS, I18nUtil.message("user.login.success")));
        return StpUtil.getTokenValue();
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + CharSequenceUtil.blankToDefault(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new BizException("验证码失效");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new BizException("验证码错误");
        }
    }


    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtil.getIpAddr());
        sysUser.setLoginDate(DateUtil.date());
        userService.updateUserProfile(sysUser);
    }
}
