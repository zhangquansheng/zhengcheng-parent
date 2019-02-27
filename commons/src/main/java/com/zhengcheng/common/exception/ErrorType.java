package com.zhengcheng.common.exception;


import lombok.Getter;

/**
 * 错误类型
 *
 * @author :    zqs
 * @Filename :     ErrorType.java
 * @Package :     com.zhengcheng.common.exception
 * @Description :
 * @date :    2019/1/25 19:47
 */
@Getter
public enum ErrorType {

    SYSTEM_ERROR("-1", "系统异常"),

    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),
    SYSTEM_NO_PERMISSION("000002", "无权限"),

    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    GATEWAY_ERROR("010500", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),

    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    UPLOAD_FILE_SIZE_LIMIT("020001", "上传文件大小超过限制");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    ErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }
}