package com.zhengcheng.web;

import com.zhengcheng.common.constant.CommonConstants;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Objects;

/**
 * Result
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1129940056717037765L;

    private Integer code;
    private String message;
    private T data;
    private String requestId;

    public static <T> Result<T> create(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static <T> Result<T> create(Integer code, String message, T data) {
        return new Result(code, message, data);
    }

    public static <T> Result<T> create(CodeEnum codeEnum) {
        return new Result(codeEnum.getCode(), codeEnum.getMessage(), null);
    }

    public static <T> Result<T> successData(String message, T data) {
        return create(CodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> successMessage(String message) {
        return successData(message, null);
    }

    public static <T> Result<T> successData(T data) {
        return successData(CodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return successMessage(CodeEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> errorData(String message, T data) {
        return create(CodeEnum.ERROR.getCode(), message, data);
    }

    public static <T> Result<T> error() {
        return errorMessage(CodeEnum.ERROR.getMessage());
    }

    public static <T> Result<T> errorData(T data) {
        return errorData(CodeEnum.ERROR.getMessage(), data);
    }

    public static <T> Result<T> errorMessage(String message) {
        return errorData(message, null);
    }

    public static <T> Result<T> selectiveMessage(boolean success, String successMessage, String errorMessage) {
        return success ? successMessage(successMessage) : errorMessage(errorMessage);
    }

    public static <T> Result<T> selectiveMessage(boolean success, String successMessage, String errorMessage, T data) {
        return success ? successData(successMessage, data) : errorMessage(errorMessage);
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.requestId = MDC.get(CommonConstants.TRACE_ID);
    }

    public boolean suc() {
        return CodeEnum.SUCCESS.getCode().equals(this.code);
    }

    public boolean fail() {
        return !CodeEnum.SUCCESS.getCode().equals(this.code);
    }

    public boolean hasData() {
        return Objects.equals(CodeEnum.SUCCESS.getCode(), this.code) && this.data != null;
    }

    public static Result fallbackResult() {
        return Result.create(CodeEnum.FALLBACK.getCode(), CodeEnum.FALLBACK.getMessage());
    }
}
