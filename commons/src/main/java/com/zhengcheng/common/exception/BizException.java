package com.zhengcheng.common.exception;


import lombok.Data;

/**
 * 业务异常
 *
 * @author :    zqs
 * @Filename :     Result.java
 * @Package :     com.quansheng.exception
 * @Description :
 * @date :    2018/12/20 16:30
 */
@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 5402012883466443408L;
    private String code;
    private String message;

    public BizException() {
    }

    public BizException(String code) {
        this(code, (String)null);
    }

    public BizException(String code, String message) {
        this(code, message, (Throwable)null);
    }

    public BizException(String code, Throwable cause) {
        this(code, (String)null, cause);
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
