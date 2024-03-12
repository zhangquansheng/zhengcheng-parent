package com.zhengcheng.ums.service;

import com.zhengcheng.cache.redis.RedisCache;
import com.zhengcheng.mvc.utils.I18nUtil;
import com.zhengcheng.ums.common.constant.CacheConstants;
import com.zhengcheng.ums.common.constant.Constants;
import com.zhengcheng.ums.common.exception.user.UserPasswordNotMatchException;
import com.zhengcheng.ums.common.exception.user.UserPasswordRetryLimitExceedException;
import com.zhengcheng.ums.common.manager.AsyncFactory;
import com.zhengcheng.ums.common.manager.AsyncManager;
import com.zhengcheng.ums.domain.entity.SysUserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 *
 * @author ruoyi
 */
@Component
public class SysPasswordService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount:3}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime:30}")
    private int lockTime;

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUserEntity user, String username, String password) {
        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, I18nUtil.message("user.password.retry.limit.exceed", maxRetryCount, lockTime)));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, null, Constants.LOGIN_FAIL, I18nUtil.message("user.password.retry.limit.count", retryCount)));
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }


    public boolean matches(SysUserEntity user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    private void clearLoginRecordCache(String loginName) {
        if (Boolean.TRUE.equals(redisCache.hasKey(getCacheKey(loginName)))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
