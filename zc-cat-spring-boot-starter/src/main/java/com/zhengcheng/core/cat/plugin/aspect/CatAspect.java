package com.zhengcheng.core.cat.plugin.aspect;

import cn.hutool.core.text.StrBuilder;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.core.cat.plugin.AbstractPluginTemplate;
import com.zhengcheng.core.cat.plugin.annotation.CatTransaction;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * CatAspect 自定义埋点
 *
 * @author :    zhangquansheng
 * @date :    2019/12/9 13:24
 */
@Aspect
public class CatAspect extends AbstractPluginTemplate {

    @Pointcut("@annotation(com.zhengcheng.core.cat.plugin.annotation.CatTransaction)")
    public void catTransactionPointcut() {
    }

    @Override
    @Around("catTransactionPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return super.doAround(pjp);
    }

    @Override
    protected Transaction beginLog(ProceedingJoinPoint pjp) {
        CatTransaction catTransaction = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(CatTransaction.class);
        StrBuilder builder = StrBuilder.create();
        builder.append(pjp.getSignature().getDeclaringType().getSimpleName());
        builder.append(".");
        builder.append(pjp.getSignature().getName());
        if (StringUtils.isNotBlank(catTransaction.name())) {
            builder.append(catTransaction.name());
        }
        return Cat.newTransaction(catTransaction.type(), builder.toString());
    }

    @Override
    protected void endLog(Transaction transaction, Object retVal, Object... params) {

    }
}
