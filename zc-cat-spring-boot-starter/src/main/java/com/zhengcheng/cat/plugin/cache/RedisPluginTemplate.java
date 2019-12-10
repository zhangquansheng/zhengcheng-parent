package com.zhengcheng.cat.plugin.cache;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.AbstractPluginTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisPluginTemplate
 *
 * @author :    zhangquansheng
 * @date :    2019/12/10 13:51
 */
@Aspect
@ConditionalOnClass({RedisTemplate.class})
public class RedisPluginTemplate extends AbstractPluginTemplate {

    @Override
    @Around("within(org.springframework.data.redis.core.RedisTemplate)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return super.doAround(pjp);
    }

    @Override
    protected Transaction beginLog(ProceedingJoinPoint pjp) {
        String methodName = pjp.getSignature().getName();
        return Cat.newTransaction("Cache.Redis", methodName);
    }

    @Override
    protected void endLog(Transaction transaction, Object retVal, Object... params) {

    }
}
