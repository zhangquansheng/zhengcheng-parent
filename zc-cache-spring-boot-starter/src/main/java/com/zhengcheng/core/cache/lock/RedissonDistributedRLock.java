package com.zhengcheng.core.cache.lock;

import com.zhengcheng.common.lock.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redisson 分布式R锁 - 可重入锁
 *
 * @author :    zhangquansheng
 * @date :    2020/4/28 10:30
 */
public class RedissonDistributedRLock implements DistributedLock {

    private final RedissonClient redissonClient;

    public RedissonDistributedRLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void acquire(String key) {
        // 可重入锁（Reentrant Lock）
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    @Override
    public boolean acquire(String key, long maxWait, TimeUnit waitUnit) throws Exception {
        // 可重入锁（Reentrant Lock）
        RLock lock = redissonClient.getLock(key);
        return lock.tryLock(maxWait, waitUnit);
    }

    @Override
    public void release(String key) {
        // 可重入锁（Reentrant Lock）
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
