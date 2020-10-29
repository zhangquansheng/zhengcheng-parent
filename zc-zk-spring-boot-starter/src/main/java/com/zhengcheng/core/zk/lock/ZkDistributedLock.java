package com.zhengcheng.core.zk.lock;

import com.zhengcheng.common.lock.DistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * Distributed Lock http://curator.apache.org//getting-started.html
 *
 * @author :    zhangquansheng
 * @date :    2020/5/29 15:02
 */
public class ZkDistributedLock implements DistributedLock {

    private final CuratorFramework client;

    public ZkDistributedLock(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public void acquire(String key) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, key);
        lock.acquire();
    }

    @Override
    public boolean acquire(String key, long maxWait, TimeUnit waitUnit) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, key);
        return lock.acquire(maxWait, waitUnit);
    }

    @Override
    public void release(String key) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, key);
        lock.release();
    }
}
