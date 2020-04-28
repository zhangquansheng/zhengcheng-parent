package com.zhengcheng.redis.lock;

import com.zhengcheng.common.lock.AbstractDistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 分布式锁
 *
 * @author :    zhangquansheng
 * @date :    2020/4/28 10:30
 */
public class RedissonDistributedLock extends AbstractDistributedLock {

    private RedissonClient redissonClient;

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(retryTimes * sleepMillis, expire, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public boolean releaseLock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
        return true;
    }
}
