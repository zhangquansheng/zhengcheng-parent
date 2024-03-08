package com.zhengcheng.ums.common.exception.user;

import com.zhengcheng.common.enums.CodeEnum;
import com.zhengcheng.common.exception.BizException;

/**
 * 用户信息异常类
 *
 * @author ruoyi
 */
public class UserException extends BizException {
    private static final long serialVersionUID = 1L;

    public UserException(String message, Object[] args) {
        super("user", CodeEnum.INTERNAL_SERVER_ERROR.getCode(), message, args);
    }
}
