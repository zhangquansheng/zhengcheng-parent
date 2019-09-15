package com.zhengcheng.swagger.common.exception;


import lombok.Data;

/**
 * 业务异常
 *
 * @author :    zqs
 * @date :    2018/12/20 16:30
 */
@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 5402012883466443408L;
    private Integer code;
    private String message;

    public BizException() {
    }

    public BizException(Integer code) {
        this(code, (String) null);
    }

    public BizException(Integer code, String message) {
        this(code, message, (Throwable) null);
    }

    public BizException(Integer code, Throwable cause) {
        this(code, (String) null, cause);
    }

    public BizException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
