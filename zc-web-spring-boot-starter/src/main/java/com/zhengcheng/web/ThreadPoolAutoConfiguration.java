package com.zhengcheng.web;

import com.zhengcheng.web.property.ThreadPoolProperties;
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
@EnableConfigurationProperties({ThreadPoolProperties.class})
@Configuration
public class ThreadPoolAutoConfiguration {

    public ThreadPoolAutoConfiguration() {
    }

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(threadPoolProperties.getMaxPoolSize());
        threadPoolTaskExecutor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        threadPoolTaskExecutor.setThreadNamePrefix("taskExecutor-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
