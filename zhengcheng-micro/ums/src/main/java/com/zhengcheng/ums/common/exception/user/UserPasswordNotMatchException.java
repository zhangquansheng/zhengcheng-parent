package com.zhengcheng.ums.common.exception.user;

import com.zhengcheng.common.exception.BizException;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends BizException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match");
    }
}
