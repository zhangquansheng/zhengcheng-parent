package com.zhengcheng.common.enums;

/**
 * 错误码
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.enums
 * @Description :
 * @date :    2019/3/30 19:32
 */
public enum ErrorCodeEnum {

    BIZ1001("1001", "接口返回数据异常");

    private String code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the string
     */
    public String code() {
        return code;
    }

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(String code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code().equals(code)) {
                return ele;
            }
        }
        return null;
    }
}
