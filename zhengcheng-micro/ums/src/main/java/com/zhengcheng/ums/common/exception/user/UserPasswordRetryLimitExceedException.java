package com.zhengcheng.ums.common.exception.user;

import com.zhengcheng.mvc.utils.I18nUtil;

/**
 * 用户错误最大次数异常类
 *
 * @author ruoyi
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount, int lockTime) {
        super(I18nUtil.message("user.password.retry.limit.exceed", new Object[]{retryLimitCount, lockTime}), null);
    }
}
