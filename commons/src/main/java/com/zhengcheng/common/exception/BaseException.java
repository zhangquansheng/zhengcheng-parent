package com.zhengcheng.common.exception;

import lombok.Getter;

/**
 * 异常
 *
 * @author :    quansheng.zhang
 * @Filename :     BaseException.java
 * @Package :     com.zhengcheng.common.exception
 * @Description :
 * @date :    2019/1/25 20:02
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 异常对应的错误类型
     */
    private ErrorType errorType;

    /**
     * 默认是系统异常
     */
    public BaseException() {
        this.errorType = ErrorType.SYSTEM_ERROR;
    }

    public BaseException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
}

