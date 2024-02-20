package com.zhengcheng.common.exception;

/**
 * 幂等性异常
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/23 0:50
 */
public class IdempotentException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    public IdempotentException(String message) {
        super(message);
    }
}
