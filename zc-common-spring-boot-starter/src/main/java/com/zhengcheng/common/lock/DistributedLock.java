package com.zhengcheng.common.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁顶级接口
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/23 0:48
 */
public interface DistributedLock {

    /**
     * 获取锁
     *
     * @param key KEY
     * @throws Exception 异常
     */
    void acquire(String key) throws Exception;

    /**
     * 获取锁
     *
     * @param key      KEY
     * @param maxWait  最大等待时间
     * @param waitUnit 等待时间单位
     * @return 成功/失败
     * @throws Exception 异常
     */
    boolean acquire(String key, long maxWait, TimeUnit waitUnit) throws Exception;

    /**
     * 释放锁
     *
     * @param key KEY
     * @throws Exception 异常
     */
    void release(String key) throws Exception;
}
