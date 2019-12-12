package com.zhengcheng.web;

import com.zhengcheng.web.property.ExecutorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池自动配置
 *
 * @author :    zhangquansheng
 * @date :    2019/12/12 12:49
 */
@EnableAsync
@EnableConfigurationProperties({ExecutorProperties.class})
@Configuration
public class TaskPoolAutoConfiguration {

    public TaskPoolAutoConfiguration() {
    }

    @Autowired
    private ExecutorProperties executorProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorProperties.getCorePoolSize());
        executor.setMaxPoolSize(executorProperties.getMaxPoolSize());
        executor.setQueueCapacity(executorProperties.getMaxPoolSize());
        executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
