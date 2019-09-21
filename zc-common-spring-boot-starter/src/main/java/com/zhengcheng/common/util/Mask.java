package com.zhengcheng.common.util;


import org.apache.commons.lang3.StringUtils;

/**
 * 手机号脱敏工具类
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:19
 */
public class Mask {

    public static String mask4Mobile(String mobile) {
        return maskString(mobile, 3, 4);
    }

    public static String mask8Mobile(String mobile) {
        return maskString(mobile, 3, 8);
    }

    public static String mask7Mobile(String mobile) {
        return maskString(mobile, 0, 7);
    }

    public static String maskString(String input, int start, int length) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        int totalLen = input.length();
        if (start >= totalLen) {
            return input;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (start + length >= totalLen) {
            for (int i = 0; i < totalLen; i++) {
                if (i < start) {
                    stringBuilder.append(input.charAt(i));
                } else {
                    stringBuilder.append("*");
                }
            }
            return stringBuilder.toString();
        } else {
            for (int i = 0; i < totalLen; i++) {
                if (i < start || i >= start + length) {
                    stringBuilder.append(input.charAt(i));
                } else {
                    stringBuilder.append("*");
                }
            }
            return stringBuilder.toString();
        }
    }

}
