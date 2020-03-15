package com.zhengcheng.async;

import com.zhengcheng.async.properties.ExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/15 12:47
 */
@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ExecutorProperties.class)
public class AsyncAutoConfiguration {

    @Autowired
    private ExecutorProperties executorProperties;

    @Bean
    public Executor defaultExecutor() {
        log.info("Start the registration of asynchronous thread pool===>" + executorProperties.toString());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorProperties.getCorePoolSize());
        executor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        executor.setQueueCapacity(executorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        executor.setAwaitTerminationSeconds(executorProperties.getAwaitTerminationSeconds());
        executor.setThreadNamePrefix(executorProperties.getThreadNamePrefix());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // caller-runs：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
