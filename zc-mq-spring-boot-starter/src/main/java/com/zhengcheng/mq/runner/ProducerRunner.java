package com.zhengcheng.mq.runner;

import com.aliyun.openservices.ons.api.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import javax.annotation.PreDestroy;

/**
 * ProducerRunner
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Slf4j
public class ProducerRunner implements CommandLineRunner {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("Producer server starting");
        Producer producer = applicationContext.getBean(Producer.class);
        producer.start();
    }

    @PreDestroy
    public void stop() {
        log.info("Producer server shutdown");
        Producer producer = applicationContext.getBean(Producer.class);
        producer.shutdown();
    }
}
