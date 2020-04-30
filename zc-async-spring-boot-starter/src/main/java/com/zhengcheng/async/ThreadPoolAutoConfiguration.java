package com.zhengcheng.async;

import com.zhengcheng.async.decorator.MdcTaskDecorator;
import com.zhengcheng.async.executor.VisibleThreadPoolTaskExecutor;
import com.zhengcheng.async.properties.ExecutorProperties;
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
@EnableConfigurationProperties({ExecutorProperties.class})
@EnableAsync
@Configuration
public class ThreadPoolAutoConfiguration {

    private final ExecutorProperties executorProperties;

    public ThreadPoolAutoConfiguration(ExecutorProperties executorProperties) {
        this.executorProperties = executorProperties;
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorProperties.getCorePoolSize());
        executor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        executor.setQueueCapacity(executorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        executor.setAwaitTerminationSeconds(executorProperties.getAwaitTerminationSeconds());
        executor.setThreadNamePrefix(executorProperties.getThreadNamePrefix());
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
