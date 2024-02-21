package com.zhengcheng.cache.queue;

/**
 * RedisDelayQueueConsumer
 *
 * @author quansheng1.zhang
 * @since 2023/9/6 16:28
 */
public interface RedisDelayQueueConsumer<T> {

    /**
     * 获取延时队列名称
     *
     * @return 延时队列的名称
     */
    String queueName();

    /**
     * 延迟队列任务执行方法，实现该方法执行具体业务
     *
     * @param t 具体任务参数
     */
    void execute(T t);

}
