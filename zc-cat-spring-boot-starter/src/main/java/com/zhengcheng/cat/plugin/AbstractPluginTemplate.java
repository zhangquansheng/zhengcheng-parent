package com.zhengcheng.cat.plugin;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * DefaultPluginTemplate
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 23:04
 */
@Slf4j
public abstract class AbstractPluginTemplate implements PluginTemplate {

    @Override
    public void doBefore(JoinPoint joinPoint) {

    }

    @Override
    public void doAfter(JoinPoint joinPoint) {

    }

    @Override
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return proxyCollector(pjp);
    }

    @Override
    public void doReturn(JoinPoint joinPoint, Object retVal) {

    }

    @Override
    public void doThrowing(JoinPoint joinPoint, Throwable ex) {

    }

    private Object proxyCollector(ProceedingJoinPoint pjp) throws Throwable {
        Transaction transaction = proxyBeginLog(pjp);
        Object obj = null;
        try {
            obj = pjp.proceed();
            proxySuccess(transaction);
            return obj;
        } catch (Throwable e) {
            exception(transaction, e);
            throw e;
        } finally {
            proxyEndLog(transaction, obj, pjp.getArgs());
        }
    }

    private boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    private boolean isNull(Object obj) {
        return obj == null;
    }

    private Transaction proxyBeginLog(ProceedingJoinPoint pjp) {
        try {
            return beginLog(pjp);
        } catch (Throwable e) {
            log.error("proxyBeginLog Exception Message: {}", e.getMessage(), e);
        }
        return null;
    }

    private void proxySuccess(Transaction transaction) {
        try {
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Throwable e) {
            log.error("proxySuccess Exception Message: {}", e.getMessage(), e);
        }
    }

    private void exception(Transaction transaction, Throwable t) {
        try {
            if (isNotNull(transaction)) {
                transaction.setStatus(t);
                Cat.logError(t);
            }
        } catch (Throwable e) {
            log.error("exception Exception Message: {}", e.getMessage(), e);
        }
    }

    protected void proxyEndLog(Transaction transaction, Object retVal, Object... params) {
        try {
            if (isNotNull(transaction)) {
                endLog(transaction, retVal, params);
            }
        } catch (Throwable e) {
            log.error("proxyEndLog Exception Message: {}", e.getMessage(), e);
        } finally {
            if (isNotNull(transaction)) {
                transaction.complete();
            }
        }
    }

    /**
     * 方法执行前开始埋点
     *
     * @param pjp 方法执行上下文
     * @return 埋点生成的transaction对象   注：可在其中加入若干event
     */
    protected abstract Transaction beginLog(ProceedingJoinPoint pjp);

    /**
     * 方法执行后进行收尾工作
     *
     * @param transaction beginLog中生成的transaction对象，注：不用手动调用complete结束，模板已调用
     * @param retVal      方法返回结果
     * @param params      方法调用时传入参数
     */
    protected abstract void endLog(Transaction transaction, Object retVal, Object... params);

}
