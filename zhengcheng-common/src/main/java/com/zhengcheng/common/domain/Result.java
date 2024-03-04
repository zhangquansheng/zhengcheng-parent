package com.zhengcheng.common.domain;

import com.zhengcheng.common.enums.CodeEnum;

import java.util.HashMap;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@NoArgsConstructor
@Data
public class Result<T> extends HashMap<String, Object> {
    private static final long serialVersionUID = 1129940056717037765L;

    private Integer code;
    private String message;
    private T data;

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

    public static <T> Result<T> error(String message) {
        return errorData(message, null);
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

    public static <T> Result<T> fallbackResult() {
        return Result.create(CodeEnum.FALLBACK.getCode(), CodeEnum.FALLBACK.getMessage());
    }

    public static <T> Result<T> ok() {
        return new Result<T>();
    }

    public static <T> Result<T> ok(Object data) {
        Result<T> r = new Result<T>();
        r.put("data", data);
        return r;
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Result put(Object value) {
        super.put("data", value);
        return this;
    }

    public Result put(PageResult pageResult) {
        super.put("rows", pageResult.getRows());
        super.put("total", pageResult.getTotal());
        return this;
    }
}
