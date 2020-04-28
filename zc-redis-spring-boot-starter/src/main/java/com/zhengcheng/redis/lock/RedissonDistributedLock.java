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

    public RedissonDistributedLock(RedissonClient redissonClient) {
        super();
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
        // 可重入锁（Reentrant Lock）
        RLock lock = redissonClient.getLock(key);
        try {
            // 尝试加锁，最多等待retryTimes * sleepMillis 毫秒，上锁以后 expire 毫秒自动解锁
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
