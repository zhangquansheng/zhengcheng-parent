package com.zhengcheng.cache.ratelimit.handler;

/**
 * LimitKeyHandler
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:15
 */
public interface LimitKeyHandler {

    /**
     * 获取用户唯一key
     *
     * @return
     */
    String getUserKey();


    /**
     * 获取 ip
     *
     * @return
     */
    String getIpKey();
}

