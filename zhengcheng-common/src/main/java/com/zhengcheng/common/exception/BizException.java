package com.zhengcheng.common.exception;


import com.zhengcheng.common.enums.CodeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author :    zqs
 * @date :    2018/12/20 16:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 5402012883466443408L;
    /**
     * 所属模块
     */
    private String module;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;
    /**
     * 错误消息对应的参数
     */
    private Object[] args;
    /**
     * 默认的错误消息
     */
    private String defaultMessage;

    public BizException() {
    }

    public BizException(String module, String message, Object[] args, String defaultMessage) {
        this.module = module;
        this.message = message;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public BizException(String module, Integer code, String message, Object[] args) {
        this.module = module;
        this.code = code;
        this.message = message;
        this.args = args;
    }

    public BizException(String message) {
        this.code = CodeEnum.INTERNAL_SERVER_ERROR.getCode();
        this.message = message;
    }

}
