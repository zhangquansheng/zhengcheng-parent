package com.zhengcheng.mvc.enumeration;

/**
 * 限制器类型
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.mvc.enumeration
 * @Description :
 * @date :    2019/3/28 23:36
 */
public enum RateLimiterType {
    /**
     * 根据请求者IP
     */
    IP,
    /**
     * 当前用户
     */
    CURRENT_USER
}
