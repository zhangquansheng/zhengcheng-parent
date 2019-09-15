package com.zhengcheng.swagger.mq.factory;

import com.google.common.collect.Maps;
import com.zhengcheng.swagger.mq.annotation.Event;
import com.zhengcheng.swagger.mq.handler.IConsumerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消费者工厂
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:21
 */
@Slf4j
@Component
public class ConsumerFactory implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    public static Map<String, Class<IConsumerHandler>> consumerHandlerBeanMap = Maps.newConcurrentMap();

    /**
     * 获取实体
     *
     * @param event 事件
     * @return
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
        Map<String, Object> evenMap = applicationContext.getBeansWithAnnotation(Event.class);
        evenMap.forEach((k, v) -> {
            Class<IConsumerHandler> consumerHandlerClass = (Class<IConsumerHandler>) v.getClass();
            for (String e : consumerHandlerClass.getAnnotation(Event.class).value()) {
                consumerHandlerBeanMap.put(e, consumerHandlerClass);
            }
        });
    }
}
