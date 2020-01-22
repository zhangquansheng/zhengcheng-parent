package com.zhengcheng.aliyun.mq.runner;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.Consumer;
import com.zhengcheng.aliyun.mq.factory.ConsumerFactory;
import com.zhengcheng.aliyun.mq.handler.IConsumerHandler;
import com.zhengcheng.aliyun.mq.properties.ConsumerProperties;
import com.zhengcheng.aliyun.mq.properties.SubscriptionTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * ConsumerRunner
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Slf4j
public class ConsumerRunner implements CommandLineRunner {

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private ConsumerProperties consumerProperties;
    @Autowired
    private ConsumerFactory consumerFactory;

    @Override
    public void run(String... strings) throws Exception {
        Consumer consumer = applicationContext.getBean(Consumer.class);
        List<SubscriptionTable> subscriptions = consumerProperties.getSubscriptions();
        if (!CollectionUtils.isEmpty(subscriptions)) {
            subscriptions.forEach(subscriptionTable -> {
                log.info("subscribe topic:{},expression:{}", subscriptionTable.getTopic(), subscriptionTable.getExpression());
                consumer.subscribe(subscriptionTable.getTopic(), subscriptionTable.getExpression(), (message, context) -> {
                    String event = message.getTag();
                    String body = new String(message.getBody());
                    IConsumerHandler consumerHandler = consumerFactory.create(event);
                    if (consumerHandler != null) {
                        log.info("Receive: event: {}, body: {}", event, body);
                        return consumerHandler.execute(body);
                    } else {
                        log.error("commit message, but create handler IllegalArgumentException, event:{}, body:{}", event, body);
                    }
                    return Action.CommitMessage;
                });
            });
        }
        consumer.start();
        log.info("Consumer server started");
    }

    @PreDestroy
    public void stop() {
        log.info("Consumer server shutdown");
        Consumer consumer = applicationContext.getBean(Consumer.class);
        consumer.shutdown();
    }

}
