package com.zhengcheng.web.interceptor;

import com.zhengcheng.web.util.AspectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 拦截所有接口，打印日志
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Slf4j
@Aspect
public class ControllerInterceptor {

    @Autowired
    private AspectUtil aspectUtil;

    /**
     * 定义拦截规则：
     * 有@RequestMapping注解的方法。
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) {
        return aspectUtil.proceed(pjp);
    }


}