package com.zhengcheng.redis.factory;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.redis.annotation.RequestLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 限流注解工厂
 *
 * @author :    quansheng.zhang
 * @date :    2020/5/12 23:24
 */
@Slf4j
public class RequestLimitFactory implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Set<String> requestLimitSet = new HashSet<>();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                RequestLimit requestLimit = AnnotationUtils.findAnnotation(method, RequestLimit.class);
                if (Objects.isNull(requestLimit)) {
                    continue;
                }

                String name = requestLimit.name();
                if (StrUtil.isBlank(name)) {
                    continue;
                }
                if (requestLimitSet.contains(name)) {
                    throw new RuntimeException("RequestLimit[" + name + "] naming conflicts.");
                } else {
                    requestLimitSet.add(name);
                    log.info("Generating unique request-limit operation named: {}", name);
                }
            }
        }
    }
}
