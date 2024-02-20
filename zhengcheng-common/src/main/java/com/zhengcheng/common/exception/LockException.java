package com.zhengcheng.common.exception;

/**
 * 分布式锁异常
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/23 0:50
 */
public class LockException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    public LockException(String message) {
        super(message);
    }
}
