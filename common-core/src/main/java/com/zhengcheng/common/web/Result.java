package com.zhengcheng.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * web端的返回结果
 *
 * @author :    zqs
 * @date :    2018/12/20 16:30
 */
@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3032015199552656978L;
    private static final String SUCCESS = "1";
    private static final String ERROR = "0";
    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;
    @ApiModelProperty(value = "处理结果描述信息")
    private String message;
    @ApiModelProperty(value = "处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> Result<T> create(String code, String message) {
        return new Result(code, message, (Object) null);
    }

    public static <T> Result<T> create(String code, String message, T data) {
        return new Result(code, message, data);
    }

    public static <T> Result<T> successData(String message, T data) {
        return create("0", message, data);
    }

    public static <T> Result<T> successMessage(String message) {
        return successData(message, null);
    }

    public static <T> Result<T> successData(T data) {
        return successData((String) null, data);
    }

    public static <T> Result<T> success() {
        return successMessage((String) null);
    }

    public static <T> Result<T> errorData(String message, T data) {
        return create("1", message, data);
    }

    public static <T> Result<T> error() {
        return errorMessage((String) null);
    }

    public static <T> Result<T> errorData(T data) {
        return errorData((String) null, data);
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

    public static <T> Result<T> error(Error e) {
        return e == null ? null : create("1", e.getMessage());
    }

    public static <T> Result<T> error(Exception e) {
        return e == null ? null : create("1", e.getMessage());
    }

    public Result() {
        this.setMessage("");
    }

    Result(String code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message == null ? "" : message;
    }
}
