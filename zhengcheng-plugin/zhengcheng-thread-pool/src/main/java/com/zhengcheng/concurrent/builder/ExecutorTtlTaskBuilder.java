package com.zhengcheng.concurrent.builder;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.zhengcheng.concurrent.executor.VisibleThreadPoolTaskExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@link Executor} 建造者
 *
 * @author :    quansheng.zhang
 * @date :    2020/4/30 19:42
 * @since : 4.3.0
 */
public class ExecutorTtlTaskBuilder implements cn.hutool.core.builder.Builder<Executor> {

    private static final long serialVersionUID = 9148979860793449313L;
    /**
     * 核心线程数：线程池创建时候初始化的线程数
     */
    private int corePoolSize = 10;
    /**
     * 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
     */
    private int maxPoolSize = 20;
    /**
     * 缓冲队列：用来缓冲执行任务的队列
     */
    private int queueCapacity = 2000;
    /**
     * 允许线程的空闲时间(秒)：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
     */
    private int keepAliveSeconds = 300;
    /**
     * 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
     */
    private int awaitTerminationSeconds = 10;
    /**
     * 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
     */
    private String threadNamePrefix = "async-service-executor";


    /**
     * 创建ExecutorMdcTaskBuilder，开始构建
     *
     * @return {@link ExecutorTtlTaskBuilder}
     */
    public static ExecutorTtlTaskBuilder create() {
        return new ExecutorTtlTaskBuilder();
    }

    /**
     * 构建ThreadPoolTaskExecutor
     */
    @Override
    public Executor build() {
        return build(this);
    }

    /**
     * 构建ThreadPoolExecutor
     *
     * @param builder {@link ExecutorTtlTaskBuilder}
     * @return {@link ThreadPoolTaskExecutor}
     */
    public Executor build(ExecutorTtlTaskBuilder builder) {
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        executor.setCorePoolSize(builder.corePoolSize);
        executor.setMaxPoolSize(builder.maxPoolSize);
        executor.setQueueCapacity(builder.queueCapacity);
        executor.setKeepAliveSeconds(builder.keepAliveSeconds);
        executor.setThreadNamePrefix(builder.threadNamePrefix);
        executor.setAwaitTerminationSeconds(builder.awaitTerminationSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        //采用ttl对相应的线程池进行包装
        return TtlExecutors.getTtlExecutor(executor.getThreadPoolExecutor());
    }

}
