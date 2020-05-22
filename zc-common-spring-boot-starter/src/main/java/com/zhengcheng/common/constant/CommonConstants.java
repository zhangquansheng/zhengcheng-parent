package com.zhengcheng.common.constant;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.Getter;

/**
 * 全局公共常量
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
public interface CommonConstants {

    @Getter
    enum EnvEnum {
        LOCAL("LOCAL", "本地环境"),
        DEV("DEV", "开发环境"),
        FAT("FAT", "测试环境"),
        UAT("UAT", "预生产环境"),
        PRO("PRO", "生产环境");
        private final String value;
        private final String desc;

        EnvEnum(final String value, final String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";
    /**
     * 系统默认最大单页限制数量
     */
    Integer DEFAULT_PAGINATION_LIMIT = 100;
    /**
     * 锁KEY的前缀
     */
    String LOCK_KEY_PREFIX = "zc:lock:";

    /**
     * 接口限流缓存KEY的前缀
     */
    String REQUEST_LIMIT_KEY_PREFIX = "zc:rl:";

    /**
     * 路径ID
     */
    String TRACE_ID = "X-ZHENGCHENG-TRACE-ID";

    /**
     * 请求ID
     */
    String REQUEST_ID = "requestId";

    /**
     * 时间戳
     */
    String SIGN_AUTH_TIMESTAMP = "timestamp";
    /**
     * 随机字符串
     */
    String SIGN_AUTH_NONCE_STR = "nonceStr";

    /**
     * 签名
     */
    String SIGN_AUTH_SIGNATURE = "signature";

    /**
     * 签名秘钥
     */
    String SIGN_AUTH_KEY = "key";

    String UNKNOWN = "unknown";

    String LOCAL_HOST_IPV4 = "127.0.0.1";

    String LOCAL_HOST_IPV6 = "0:0:0:0:0:0:0:1";

    Integer IPS_MAX_LENGTH = 15;

    // ---------------------------------------------------------------- stringPool

    String AMPERSAND = "&";
    String AND = "and";
    String AT = "@";
    String ASTERISK = "*";
    String STAR = ASTERISK;
    String BACK_SLASH = "\\";
    String COLON = ":";
    String COMMA = ",";
    String DASH = "-";
    String DOLLAR = "$";
    String DOT = ".";
    String DOTDOT = "..";
    String DOT_CLASS = ".class";
    String DOT_JAVA = ".java";
    String DOT_XML = ".xml";
    String EMPTY = "";
    String EQUALS = "=";
    String FALSE = "false";
    String SLASH = "/";
    String HASH = "#";
    String HAT = "^";
    String LEFT_BRACE = "{";
    String LEFT_BRACKET = "(";
    String LEFT_CHEV = "<";
    String DOT_NEWLINE = ",\n";
    String NEWLINE = "\n";
    String N = "n";
    String NO = "no";
    String NULL = "null";
    String OFF = "off";
    String ON = "on";
    String PERCENT = "%";
    String PIPE = "|";
    String PLUS = "+";
    String QUESTION_MARK = "?";
    String EXCLAMATION_MARK = "!";
    String QUOTE = "\"";
    String RETURN = "\r";
    String TAB = "\t";
    String RIGHT_BRACE = "}";
    String RIGHT_BRACKET = ")";
    String RIGHT_CHEV = ">";
    String SEMICOLON = ";";
    String SINGLE_QUOTE = "'";
    String BACKTICK = "`";
    String SPACE = " ";
    String TILDA = "~";
    String LEFT_SQ_BRACKET = "[";
    String RIGHT_SQ_BRACKET = "]";
    String TRUE = "true";
    String UNDERSCORE = "_";
    String UTF_8 = "UTF-8";
    String US_ASCII = "US-ASCII";
    String ISO_8859_1 = "ISO-8859-1";
    String Y = "y";
    String YES = "yes";
    String ONE = "1";
    String ZERO = "0";
    String DOLLAR_LEFT_BRACE = "${";
    String HASH_LEFT_BRACE = "#{";
    String CRLF = "\r\n";

    String HTML_NBSP = "&nbsp;";
    String HTML_AMP = "&amp";
    String HTML_QUOTE = "&quot;";
    String HTML_LT = "&lt;";
    String HTML_GT = "&gt;";

    // ---------------------------------------------------------------- array

    String[] EMPTY_ARRAY = new String[0];

    byte[] BYTES_NEW_LINE = StringPool.NEWLINE.getBytes();
}
