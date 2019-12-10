package com.zhengcheng.cat.plugin.feign;

import cn.hutool.core.text.StrBuilder;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.AbstractPluginTemplate;
import com.zhengcheng.cat.plugin.common.CatMsgConstants;
import com.zhengcheng.cat.plugin.common.CatMsgContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
        this.createMessageTree();
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

    /**
     * 统一设置消息编号的messageId
     */
    private void createMessageTree() {
        CatMsgContext context = new CatMsgContext();
        Cat.logRemoteCallClient(context);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute(Cat.Context.PARENT, context.getProperty(Cat.Context.PARENT), 0);
        requestAttributes.setAttribute(Cat.Context.ROOT, context.getProperty(Cat.Context.ROOT), 0);
        requestAttributes.setAttribute(Cat.Context.CHILD, context.getProperty(Cat.Context.CHILD), 0);
        requestAttributes.setAttribute(CatMsgConstants.APPLICATION_KEY, Cat.getManager().getDomain(), 0);
    }
}
