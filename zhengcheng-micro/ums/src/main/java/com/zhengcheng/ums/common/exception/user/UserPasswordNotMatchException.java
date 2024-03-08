package com.zhengcheng.ums.common.exception.user;

import com.zhengcheng.mvc.utils.I18nUtil;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super(I18nUtil.message("user.password.not.match"), null);
    }
}
