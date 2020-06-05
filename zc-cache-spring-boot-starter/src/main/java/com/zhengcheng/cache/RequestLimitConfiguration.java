package com.zhengcheng.cache;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.cache.annotation.RequestLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 接口限流注解配置
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:05
 */
@Slf4j
@Configuration
public class RequestLimitConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 验证RequestLimit注解是否配置正确
        Set<String> requestLimitSet = new HashSet<>();
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class, false, true);

        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);

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
                    throw new RuntimeException("request-limit[" + name + "] naming conflicts.");
                } else {
                    requestLimitSet.add(name);
                    log.info("Generating unique request-limit  named: {}", name);
                }
            }
        }
    }
}
