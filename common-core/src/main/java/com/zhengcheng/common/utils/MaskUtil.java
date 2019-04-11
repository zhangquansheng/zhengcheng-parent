package com.zhengcheng.common.utils;

import org.nutz.lang.Strings;

/**
 * 脱敏工具类
 *
 * @author :    quansheng.zhang
 * @Filename :     Mask.java
 * @Package :     com.zhengcheng.upms.utils
 * @Description :
 * @date :    2019/1/26 7:49
 */
public class MaskUtil {

    public MaskUtil() {
    }

    public static String mask7Mobile(String mobile) {
        return maskString(mobile, 0, 7);
    }

    public static String mask4Mobile(String mobile) {
        return maskString(mobile, 3, 4);
    }

    public static String mask8Mobile(String mobile) {
        return maskString(mobile, 3, 8);
    }

    public static String maskString(String input, int start, int length) {
        input = Strings.sBlank(input);
        int totalLen = input.length();
        if (start >= totalLen) {
            return input;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            int i;
            if (start + length >= totalLen) {
                for (i = 0; i < totalLen; ++i) {
                    if (i < start) {
                        stringBuilder.append(input.charAt(i));
                    } else {
                        stringBuilder.append("*");
                    }
                }

                return stringBuilder.toString();
            } else {
                for (i = 0; i < totalLen; ++i) {
                    if (i >= start && i < start + length) {
                        stringBuilder.append("*");
                    } else {
                        stringBuilder.append(input.charAt(i));
                    }
                }

                return stringBuilder.toString();
            }
        }
    }
}
