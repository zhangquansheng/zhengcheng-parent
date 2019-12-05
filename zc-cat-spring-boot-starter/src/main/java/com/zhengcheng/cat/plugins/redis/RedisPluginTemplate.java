package com.zhengcheng.cat.plugins.redis;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugins.AbstractPluginTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import redis.clients.jedis.BinaryClient;

/**
 * RedisPluginTemplate
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 23:14
 */
@Slf4j
@Aspect
public class RedisPluginTemplate extends AbstractPluginTemplate {

    /**
     * 定义拦截规则
     */
    @Pointcut("")
    public void pointcut() {
    }

    @Override
    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return super.doAround(pjp);
    }

    @Override
    public Transaction beginLog(ProceedingJoinPoint pjp) {
        Transaction transaction = null;
        BinaryClient jedis = (BinaryClient) pjp.getTarget();
        if (jedis != null) {
            transaction = Cat.newTransaction("Cache.Redis_" + jedis.getHost(), pjp.getSignature().toString());
        }
        return transaction;
    }

    @Override
    public void endLog(Transaction transaction, Object retVal, Object... params) {

    }
}
