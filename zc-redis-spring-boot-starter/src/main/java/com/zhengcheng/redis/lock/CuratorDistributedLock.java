package com.zhengcheng.redis.lock;

import com.zhengcheng.common.lock.AbstractDistributedLock;

/**
 * Curator实现zk分布式锁工具类
 *
 * @author :    zhangquansheng
 * @date :    2020/4/28 10:54
 */
public class CuratorDistributedLock extends AbstractDistributedLock {

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        return false;
    }
}
