package com.zhengcheng.aliyun.mq.factory;

import com.aliyun.openservices.ons.api.Action;
import com.google.common.collect.Maps;
import com.zhengcheng.aliyun.mq.annotation.Event;
import com.zhengcheng.aliyun.mq.annotation.RocketmqListener;
import com.zhengcheng.aliyun.mq.handler.IConsumerHandler;
import com.zhengcheng.aliyun.mq.handler.impl.MethodConsumerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 消费者工厂
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:21
 */
@Slf4j
public class ConsumerFactory implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    public static Map<String, Class<IConsumerHandler>> consumerHandlerBeanMap = Maps.newConcurrentMap();

    /**
     * 获取实体
     *
     * @param event 事件
     * @return IConsumerHandler
     */
    public IConsumerHandler create(String event) {
        Class<IConsumerHandler> consumerHandlerClass = consumerHandlerBeanMap.get(event);
        if (consumerHandlerClass == null) {
            return null;
        }
        return applicationContext.getBean(consumerHandlerClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        // init ConsumerHandler Repository
        initConsumerHandlerRepository(applicationContext);

        // init ConsumerHandler Repository (for method)
        initConsumerHandlerMethodRepository(applicationContext);
    }

    private void initConsumerHandlerRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }
        Map<String, Object> evenMap = applicationContext.getBeansWithAnnotation(Event.class);
        evenMap.forEach((k, v) -> {
            Class<IConsumerHandler> consumerHandlerClass = (Class<IConsumerHandler>) v.getClass();
            for (String tag : consumerHandlerClass.getAnnotation(Event.class).tags()) {
                consumerHandlerBeanMap.put(tag, consumerHandlerClass);
            }
        });
    }

    private void initConsumerHandlerMethodRepository(ApplicationContext applicationContext) {
        if (applicationContext == null) {
            return;
        }

        // init consumer handler from method
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                RocketmqListener rocketmqListener = AnnotationUtils.findAnnotation(method, RocketmqListener.class);
                if (rocketmqListener != null) {
                    // tags
                    String[] tags = rocketmqListener.tags();
                    if (tags.length == 0) {
                        throw new RuntimeException("rocketmq-listener method-consumerHandler name invalid, for[" + bean.getClass() + "#" + method.getName() + "] .");
                    }
                    for (String tag : tags) {
                        if (consumerHandlerBeanMap.get(tag) != null) {
                            throw new RuntimeException("rocketmq-listener consumerHandler[" + tag + "] naming conflicts.");
                        }
                    }

                    // execute method
                    if (!(method.getParameterTypes() != null && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(String.class))) {
                        throw new RuntimeException("rocketmq-listener method-consumerHandler param-class-type invalid, for[" + bean.getClass() + "#" + method.getName() + "] , " +
                                "The correct method format like \" public Action execute(String param) \" .");
                    }
                    if (!method.getReturnType().isAssignableFrom(Action.class)) {
                        throw new RuntimeException("rocketmq-listener method-consumerHandler return-class-type invalid, for[" + bean.getClass() + "#" + method.getName() + "] , " +
                                "The correct method format like \" public Action execute(String param) \" .");
                    }
                    method.setAccessible(true);


                    for (String tag : tags) {
                        IConsumerHandler consumerHandler = new MethodConsumerHandler(bean, method);
                        consumerHandlerBeanMap.put(tag, (Class<IConsumerHandler>) consumerHandler.getClass());
                    }
                }
            }
        }
    }

}
