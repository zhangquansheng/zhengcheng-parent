package com.zhengcheng.common.constant;

/**
 * CommonConstant
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.constant
 * @Description :
 * @date :    2019/2/2 17:31
 */
public class CommonConstant {
    /**
     * 开发环境
     */
    public static final String ENV_DEV = "dev";

    public static final String CURRENT_USER = "currentUserId";

    public static final String UNKNOWN = "unknown";

    public static final String SUCCESS = "0";

    public static final String FALLBACK_CODE = "1001";

    public static final String FALLBACK_MSG = "接口访问超时";

    public static final String EMPTY_CODE = "1002";

    public static final String EMPTY_MSG = "无返回数据";

    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_REAL_IP = "X-Real-IP";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";


    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    public static final int MAX_IP_LENGTH = 15;

    /**
     * 符号
     */
    public static final class Symbol {
        private Symbol() {
        }

        /**
         * The constant COMMA.
         */
        public static final String COMMA = ",";
        public static final String SPOT = ".";
        /**
         * The constant UNDER_LINE.
         */
        public final static String UNDER_LINE = "_";
        /**
         * The constant PER_CENT.
         */
        public final static String PER_CENT = "%";
        /**
         * The constant AT.
         */
        public final static String AT = "@";
        /**
         * The constant PIPE.
         */
        public final static String PIPE = "||";
        public final static String SHORT_LINE = "-";
        public final static String SPACE = " ";
        public static final String SLASH = "/";
        public static final String MH = ":";

    }
}
