package com.zhengcheng.cat.plugin.spring;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.AbstractPluginTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * SpringServicePluginTemplate
 *
 * @author :    zhangquansheng
 * @date :    2019/12/6 9:17
 */
@Aspect
public class SpringServicePluginTemplate extends AbstractPluginTemplate {

    @Override
    @Around("execution(* (@org.springframework.stereotype.Service *).*(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return super.doAround(pjp);
    }


    @Override
    protected Transaction beginLog(ProceedingJoinPoint pjp) {
        StringBuilder sb = new StringBuilder();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        String methodName = pjp.getSignature().getName();
        return Cat.newTransaction("Service", sb.append(className).append(".").append(methodName).toString());
    }

    @Override
    protected void endLog(Transaction transaction, Object retVal, Object... params) {
    }
}
