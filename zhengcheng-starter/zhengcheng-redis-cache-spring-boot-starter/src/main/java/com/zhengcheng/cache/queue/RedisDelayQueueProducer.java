package com.zhengcheng.cache.queue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * QueueProducer
 *
 * @author quansheng1.zhang
 * @since 2023/9/6 16:21
 */
@Slf4j
public class RedisDelayQueueProducer {

    private final RedissonClient redissonClient;

    public RedisDelayQueueProducer(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 添加到队列
     *
     * @param t         实际业务参数参数
     * @param delay     延迟时间数
     * @param timeUnit  延迟时间单位
     * @param queueName 队列名
     * @param <T>       泛型
     */
    public <T> void addQueue(String queueName, long delay, TimeUnit timeUnit, T t) {
        log.info("添加到队列：{}, 延迟时间数：{}, 延迟时间单位：{}, 实际参数：{}", queueName, delay, timeUnit, t);
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
    }

    /**
     * 删除队列中的值
     *
     * @param queueName 队列名称
     * @param value     要删除的值
     * @param <T>       泛型
     * @return 是否删除成功
     */
    public <T> Boolean delValue(String queueName, Object value) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        return delayedQueue.remove(value);
    }

}
