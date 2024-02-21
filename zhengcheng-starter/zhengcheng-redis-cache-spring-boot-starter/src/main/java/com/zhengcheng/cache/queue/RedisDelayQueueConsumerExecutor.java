package com.zhengcheng.cache.queue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisDelayQueueConsumerExecutor 延时队列消费者执行者
 *
 * @author quansheng1.zhang
 * @since 2023/9/6 16:32
 */
@Slf4j
@SuppressWarnings("all")
public class RedisDelayQueueConsumerExecutor implements ApplicationContextAware {

    private final RedissonClient redissonClient;

    public RedisDelayQueueConsumerExecutor(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayQueueConsumer> map = applicationContext.getBeansOfType(RedisDelayQueueConsumer.class);
        map.values().forEach(this::startThread);
    }

    /**
     * 启动线程获取队列，并执行业务
     *
     * @param queueName                队列名
     * @param CommonDelayQueueConsumer 任务回调监听
     * @param <T>                      泛型
     */
    private <T> void startThread(RedisDelayQueueConsumer queueConsumer) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueConsumer.queueName());
        long logPrintInterval = 1000;
        //由于此线程需要常驻，所以可以直接新建线程，不需要交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动队列名为：{}的监听线程", queueConsumer.queueName());
            long exCount = 0;
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    queueConsumer.execute(t);

                    // 执行成功，恢复异常的计数次数
                    exCount = 0;
                } catch (Exception e) {
                    // 增加一个计时器，每隔一段时间打印一次日志
                    try {
                        long sleepMillis = logPrintInterval * (exCount + 1);
                        log.error("队列监听线程错误, sleep {}ms.", sleepMillis, e);
                        Thread.sleep(sleepMillis);
                    } catch (InterruptedException ex) {
                        log.error("队列监听线程被中断", ex);
                        Thread.currentThread().interrupt();
                    }
                    exCount++;
                }
            }
        });
        thread.setName(queueConsumer.queueName());
        thread.start();
    }

}
