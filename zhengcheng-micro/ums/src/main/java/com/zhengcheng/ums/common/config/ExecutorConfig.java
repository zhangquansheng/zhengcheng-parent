package com.zhengcheng.ums.common.config;

import com.zhengcheng.concurrent.builder.ExecutorTtlTaskBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

/**
 * 线程池配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Configuration
public class ExecutorConfig {

    @Bean("asyncTaskExecutor")
    public Executor seczoneTaskExecutor() {
        return ExecutorTtlTaskBuilder.create().build();
    }

}
