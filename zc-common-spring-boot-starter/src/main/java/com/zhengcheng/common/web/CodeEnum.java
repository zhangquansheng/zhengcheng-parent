package com.zhengcheng.common.web;

import lombok.Getter;

/**
 * 统一返回码枚举(公共错误码)
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Getter
public enum CodeEnum {

    SUCCESS(200, "请求成功"),
    ERROR(1, "请求失败"),
    FALLBACK(2, "接口访问超时"),
    BAD_REQUEST(400, "请求有误，通常由于请求参数不正确导致，请仔细检查请求参数"),
    UNAUTHORIZED(401, "登录信息已过期"),
    FORBIDDEN(403, "没有权限请求当前方法"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),
    REQUEST_EXCEED_LIMIT(406, "您的网络可能有问题，请稍后重试!"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),
    INTERNAL_SERVER_ERROR(500, "系统升级中，请稍后重试！"),
    DB_FAILED(580, "数据库操作失败"),
    EXCEED_QUOTA(588, "请求频率超出配额");

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
