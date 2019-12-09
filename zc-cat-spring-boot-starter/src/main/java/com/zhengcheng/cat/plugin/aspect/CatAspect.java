package com.zhengcheng.cat.plugin.aspect;

import cn.hutool.core.text.StrBuilder;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.annotation.CatTransaction;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * CatAspect 自定义埋点
 *
 * @author :    zhangquansheng
 * @date :    2019/12/9 13:24
 */
@Aspect
public class CatAspect {

    @Around("@annotation(com.zhengcheng.cat.plugin.annotation.CatTransaction)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        CatTransaction catTransaction = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(CatTransaction.class);
        StrBuilder builder = StrBuilder.create();
        builder.append(pjp.getSignature().getDeclaringType().getSimpleName());
        builder.append(".");
        builder.append(pjp.getSignature().getName());
        if (StringUtils.isNotBlank(catTransaction.name())) {
            builder.append(catTransaction.name());
        }
        Transaction t = Cat.newTransaction(catTransaction.type(), builder.toString());
        try {
            Object result = pjp.proceed();
            t.setStatus(Transaction.SUCCESS);
            return result;
        } catch (Throwable e) {
            t.setStatus(e);
            throw e;
        } finally {
            t.complete();
        }
    }
}
