package com.zhengcheng.swagger.common.enumeration;

/**
 * Result
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
public enum CodeEnum {

    SUCCESS(200),
    ERROR(1),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    METHOD_NOT_ALLOWED(405),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER_ERROR(500);

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
