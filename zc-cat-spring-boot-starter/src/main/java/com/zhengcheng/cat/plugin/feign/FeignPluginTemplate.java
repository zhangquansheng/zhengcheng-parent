package com.zhengcheng.cat.plugin.feign;

import cn.hutool.core.text.StrBuilder;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.AbstractPluginTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * FeignPluginTemplate
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/9 21:51
 */
@Aspect
public class FeignPluginTemplate extends AbstractPluginTemplate {

    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void feignClientPointcut() {
    }

    @Override
    @Around("feignClientPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return super.doAround(pjp);
    }

    @Override
    protected Transaction beginLog(ProceedingJoinPoint pjp) {
        StrBuilder sb = StrBuilder.create();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        String methodName = pjp.getSignature().getName();
        return Cat.newTransaction("RemoteCall", sb.append(className).append(".").append(methodName).toString());
    }

    @Override
    protected void endLog(Transaction transaction, Object retVal, Object... params) {

    }
}
