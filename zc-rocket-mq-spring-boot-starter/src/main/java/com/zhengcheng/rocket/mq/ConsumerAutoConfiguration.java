package com.zhengcheng.rocket.mq;

import cn.hutool.core.util.StrUtil;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.batch.BatchMessageListener;
import com.aliyun.openservices.ons.api.bean.BatchConsumerBean;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.OrderConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.zhengcheng.rocket.mq.annotation.RocketMQListener;
import com.zhengcheng.rocket.mq.properties.AliyunAkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ConsumerAutoConfiguration
 *
 * @author quansheng1.zhang
 * @since 2021/2/2 18:57
 */
@Slf4j
@ConditionalOnBean({AliyunAkProperties.class, StandardEnvironment.class})
@Configuration
public class ConsumerAutoConfiguration implements ApplicationContextAware, SmartInitializingSingleton {

    @Autowired
    private AliyunAkProperties aliyunAkProperties;

    @Autowired
    private StandardEnvironment environment;

    private ConfigurableApplicationContext applicationContext;

    private AtomicLong counter = new AtomicLong(0);

    public ConsumerAutoConfiguration() {
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        ConcurrentHashMap<String, Boolean> concurrentHashMap = new ConcurrentHashMap<>();

        Map<String, Object> rocketMQListenerMap = applicationContext.getBeansWithAnnotation(RocketMQListener.class);

        List<Object> rocketMQListenerObjectList = new ArrayList<>();
        rocketMQListenerMap.forEach((beanName, bean) -> rocketMQListenerObjectList.add(bean));

        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;

        rocketMQListenerMap.forEach((beanName, bean) -> {
            Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
            RocketMQListener annotation = clazz.getAnnotation(RocketMQListener.class);
            String groupId = environment.resolvePlaceholders(annotation.groupId());
            if (!concurrentHashMap.containsKey(groupId)) {
                if (bean instanceof MessageListener) {
                    String consumerBeanName = StrUtil.format("{}_{}", ConsumerBean.class.getName(), counter.incrementAndGet());
                    genericApplicationContext.registerBean(consumerBeanName, ConsumerBean.class,
                            () -> buildConsumer(groupId, rocketMQListenerObjectList),
                            beanDefinition -> beanDefinition.setDestroyMethodName("shutdown"));
                    ConsumerBean consumerBean = genericApplicationContext.getBean(consumerBeanName, ConsumerBean.class);
                    consumerBean.start();

                    log.info("Register RocketMQ {} success, {}, {}", consumerBeanName, consumerBean.getProperties(), consumerBean.getSubscriptionTable());
                } else if (bean instanceof BatchMessageListener) {
                    String batchConsumerBeanName = StrUtil.format("{}_{}", BatchConsumerBean.class.getName(), counter.incrementAndGet());
                    genericApplicationContext.registerBean(batchConsumerBeanName, BatchConsumerBean.class,
                            () -> buildBatchConsumer(groupId, rocketMQListenerObjectList),
                            beanDefinition -> beanDefinition.setDestroyMethodName("shutdown"));
                    BatchConsumerBean batchConsumerBean = genericApplicationContext.getBean(batchConsumerBeanName, BatchConsumerBean.class);
                    batchConsumerBean.start();

                    log.info("Register RocketMQ {} success, {}, {}", batchConsumerBean, batchConsumerBean.getProperties(), batchConsumerBean.getSubscriptionTable());
                } else if (bean instanceof MessageOrderListener) {
                    String orderConsumerBeanName = StrUtil.format("{}_{}", OrderConsumerBean.class.getName(), counter.incrementAndGet());
                    genericApplicationContext.registerBean(orderConsumerBeanName, OrderConsumerBean.class,
                            () -> buildOrderConsumerBean(groupId, rocketMQListenerObjectList),
                            beanDefinition -> beanDefinition.setDestroyMethodName("shutdown")
                    );
                    OrderConsumerBean orderConsumerBean = genericApplicationContext.getBean(orderConsumerBeanName, OrderConsumerBean.class);
                    orderConsumerBean.start();

                    log.info("Register RocketMQ {} success, {}, {}", orderConsumerBeanName, orderConsumerBean.getProperties(), orderConsumerBean.getSubscriptionTable());
                } else {
                    throw new IllegalStateException(StrUtil.format("{} is not support instance of RocketMQListener Annotation", beanName));
                }

                concurrentHashMap.put(groupId, Boolean.TRUE);
            }
        });
    }

    private RocketMQListener getRocketMQListenerAnnotation(Object bean) {
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
        return clazz.getAnnotation(RocketMQListener.class);
    }

    private OrderConsumerBean buildOrderConsumerBean(@NonNull String groupId, @NonNull List<Object> rocketMQListenerObjectList) {
        OrderConsumerBean orderConsumerBean = new OrderConsumerBean();

        //设置属性
        RocketMQListener firstRocketMQListenerAnnotation = getFirstRocketMQListener(groupId, rocketMQListenerObjectList);
        orderConsumerBean.setProperties(getMqProperties(groupId, firstRocketMQListenerAnnotation));

        //订阅关系
        Map<Subscription, MessageOrderListener> subscriptionTable = new HashMap<>();
        rocketMQListenerObjectList.forEach(bean -> {
            RocketMQListener annotation = getRocketMQListenerAnnotation(bean);
            if (groupId.equals(environment.resolvePlaceholders(annotation.groupId()))) {
                subscriptionTable.put(getMqSubscription(annotation), (MessageOrderListener) bean);
            }
        });
        orderConsumerBean.setSubscriptionTable(subscriptionTable);

        return orderConsumerBean;
    }

    private BatchConsumerBean buildBatchConsumer(@NonNull String groupId, @NonNull List<Object> rocketMQListenerObjectList) {
        BatchConsumerBean batchConsumerBean = new BatchConsumerBean();

        // 设置属性
        RocketMQListener firstRocketMQListenerAnnotation = getFirstRocketMQListener(groupId, rocketMQListenerObjectList);
        batchConsumerBean.setProperties(getMqProperties(groupId, firstRocketMQListenerAnnotation));

        //订阅关系
        Map<Subscription, BatchMessageListener> subscriptionTable = new HashMap<>();
        rocketMQListenerObjectList.forEach(bean -> {
            RocketMQListener annotation = getRocketMQListenerAnnotation(bean);
            if (groupId.equals(environment.resolvePlaceholders(annotation.groupId()))) {
                subscriptionTable.put(getMqSubscription(annotation), (BatchMessageListener) bean);
            }
        });
        batchConsumerBean.setSubscriptionTable(subscriptionTable);

        return batchConsumerBean;
    }

    private ConsumerBean buildConsumer(@NonNull String groupId, @NonNull List<Object> rocketMQListenerObjectList) {
        ConsumerBean consumerBean = new ConsumerBean();

        // 设置属性
        RocketMQListener firstRocketMQListenerAnnotation = getFirstRocketMQListener(groupId, rocketMQListenerObjectList);
        consumerBean.setProperties(getMqProperties(groupId, firstRocketMQListenerAnnotation));

        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
        rocketMQListenerObjectList.forEach(bean -> {
            RocketMQListener annotation = getRocketMQListenerAnnotation(bean);
            if (groupId.equals(environment.resolvePlaceholders(annotation.groupId()))) {
                subscriptionTable.put(getMqSubscription(annotation), (MessageListener) bean);
            }
        });
        consumerBean.setSubscriptionTable(subscriptionTable);

        return consumerBean;
    }

    @NonNull
    private RocketMQListener getFirstRocketMQListener(@NonNull String groupId, @NonNull List<Object> rocketMQListenerObjectList) {
        for (Object object : rocketMQListenerObjectList) {
            RocketMQListener annotation = getRocketMQListenerAnnotation(object);
            if (Objects.nonNull(annotation) && StrUtil.equalsAnyIgnoreCase(groupId, environment.resolvePlaceholders(annotation.groupId()))) {
                return annotation;
            }
        }
        throw new IllegalStateException(StrUtil.format("GroupId [{}] is not support instance of RocketMQListener Annotation", groupId));
    }

    private Subscription getMqSubscription(RocketMQListener annotation) {
        Subscription subscription = new Subscription();
        subscription.setTopic(environment.resolvePlaceholders(annotation.topic()));
        subscription.setType(annotation.type().name());
        subscription.setExpression(environment.resolvePlaceholders(annotation.expression()));
        return subscription;
    }

    private Properties getMqProperties(String groupId, RocketMQListener annotation) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, environment.resolvePlaceholders(annotation.nameSrvAddr()));
        properties.setProperty(PropertyKeyConst.GROUP_ID, groupId);
        properties.setProperty(PropertyKeyConst.AccessKey, aliyunAkProperties.getAk());
        properties.setProperty(PropertyKeyConst.SecretKey, aliyunAkProperties.getSec());
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, String.valueOf(annotation.consumeThreadNums()));
        properties.setProperty(PropertyKeyConst.MessageModel, annotation.messageModel().getModeCN());
        return properties;
    }

}
