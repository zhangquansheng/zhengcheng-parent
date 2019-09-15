package com.zhengcheng.swagger.mq.runner;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.*;
import com.zhengcheng.swagger.mq.factory.ConsumerFactory;
import com.zhengcheng.swagger.mq.handler.IConsumerHandler;
import com.zhengcheng.swagger.mq.model.Result;
import com.zhengcheng.swagger.mq.properties.ConsumerProperties;
import com.zhengcheng.swagger.mq.properties.SubscriptionTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

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
            subscriptions.stream().forEach(subscriptionTable -> {
                log.info("subscribe topic:{},expression:{}", subscriptionTable.getTopic(), subscriptionTable.getExpression());
                consumer.subscribe(subscriptionTable.getTopic(), subscriptionTable.getExpression(), new MessageListener() {
                    @Override
                    public Action consume(Message message, ConsumeContext context) {
                        String event = message.getTag();
                        String body = new String(message.getBody());
                        IConsumerHandler consumerHandler = consumerFactory.create(event);
                        if (consumerHandler != null) {
                            log.info("Receive: event: {}, body: {}", event, body);
                            Result result = consumerHandler.execute(JSONObject.parseObject(body));
                            if (result.getCode() == Result.SUCCESS_CODE) {
                                return Action.CommitMessage;
                            }
                            return Action.ReconsumeLater;
                        } else {
                            log.error("commit message, but create handler IllegalArgumentException, event:{}, body:{}", event, body);
                        }
                        return Action.CommitMessage;
                    }
                });
            });
        }
        consumer.start();
        log.info("Consumer Started");
    }

}
