package com.zhengcheng.mq.runner;

import com.aliyun.openservices.ons.api.Producer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

/**
 * ProducerRunner
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
public class ProducerRunner implements CommandLineRunner {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... strings) throws Exception {
        Producer producer = applicationContext.getBean(Producer.class);
        producer.start();
    }
}
