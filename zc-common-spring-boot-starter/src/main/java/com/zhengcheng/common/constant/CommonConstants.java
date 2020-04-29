package com.zhengcheng.common.constant;

/**
 * 全局公共常量
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
public interface CommonConstants {
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
}
