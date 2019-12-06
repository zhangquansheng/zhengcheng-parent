package com.zhengcheng.cat.plugin.spring;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.AbstractPluginTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.StringTokenizer;

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
        StringBuilder type = new StringBuilder();
        String packageStr = pjp.getSignature().getDeclaringType().getPackage().getName();
        StringTokenizer st = new StringTokenizer(packageStr, ".");
        for (int i = 0; i < 2; i++) {
            type.append(st.nextToken());
            type.append(".");
        }
        type.append("Service");
        return Cat.newTransaction(type.toString(), pjp.getSignature().toShortString());
    }

    @Override
    protected void endLog(Transaction transaction, Object retVal, Object... params) {
    }
}
