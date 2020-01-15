package com.zhengcheng.common.enumeration;

import lombok.Getter;

/**
 * Result
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Getter
public enum CodeEnum {

    SUCCESS(200, "操作成功"),
    ERROR(1, "操作失败"),
    FALLBACK(2, "接口访问超时"),
    BAD_REQUEST(400, "参数解析失败"),
    UNAUTHORIZED(401, "登录信息已过期"),
    FORBIDDEN(403, "没有权限请求当前方法"),
    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),
    INTERNAL_SERVER_ERROR(500, "系统升级中，请稍后重试！");

    /**
     * 编码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CodeEnum getEnum(Integer code) {
        for (CodeEnum ele : CodeEnum.values()) {
            if (ele.getCode().equals(code)) {
                return ele;
            }
        }
        return null;
    }

}
