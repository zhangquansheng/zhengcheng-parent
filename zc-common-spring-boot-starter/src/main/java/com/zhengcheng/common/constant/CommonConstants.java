package com.zhengcheng.common.constant;

/**
 * 全局公共常量
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
public interface CommonConstants {

    /**------------------------环境变量------------------------*/
    /**
     * 本地环境
     */
    String ENV_LOCAL = "LOCAL";
    /**
     * 开发环境
     */
    String ENV_DEV = "DEV";
    /**
     * 测试环境
     */
    String ENV_FAT = "FAT";
    /**
     * 预生产环境
     */
    String ENV_UAT = "UAT";
    /**
     * 生产环境
     */
    String ENV_PRO = "PRO";


    /**
     * 编码
     */
    String UTF8 = "UTF-8";
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
    String LOCK_KEY_PREFIX = "LOCK_KEY:";

    /**
     * 路径ID
     */
    String TRACE_ID = "X-ZHENGCHENG-TRACE-ID";

    /**
     * 请求ID
     */
    String REQUEST_ID = "requestId";

    /**------------------------签名验证------------------------*/
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

}
