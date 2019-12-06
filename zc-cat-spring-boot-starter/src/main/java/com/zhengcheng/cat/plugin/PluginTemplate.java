package com.zhengcheng.cat.plugin;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * PluginTemplate
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 23:03
 */
public interface PluginTemplate {

    void doBefore(JoinPoint joinPoint);


    void doAfter(JoinPoint joinPoint);


    Object doAround(ProceedingJoinPoint pjp) throws Throwable;


    void doReturn(JoinPoint joinPoint, Object retVal);


    void doThrowing(JoinPoint joinPoint, Throwable ex);
}
