package com.zhengcheng.redis.lock;

import com.zhengcheng.common.lock.AbstractDistributedLock;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

/**
 * Curator实现zk分布式锁工具类
 *
 * @author :    zhangquansheng
 * @date :    2020/4/28 10:54
 */
@Deprecated
@Slf4j
public class CuratorDistributedLock extends AbstractDistributedLock {

    private CuratorFramework curatorFramework;

    public CuratorDistributedLock(@NonNull CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis) {
//        if (!CuratorFrameworkState.STARTED.equals(curatorFramework.getState())) {
//            log.warn("instance must be started before calling this method");
//            return false;
//        }
//        String nodePath = "/curator/lock/%s";
//        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, String.format(nodePath, key));
//        boolean locked = mutex.acquire(acquireTimeout, TimeUnit.SECONDS);
//        ZookeeperLockContext.setContext(mutex);
//        return locked;
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
//        InterProcessLock interProcessLock = ZookeeperLockContext.getContext();
//        if (null != interProcessLock) {
//            try {
//                interProcessLock.release();
//            } catch (Exception e) {
//                log.warn("zookeeper lock release error", e);
//            }
//        }
        return false;
    }
}
