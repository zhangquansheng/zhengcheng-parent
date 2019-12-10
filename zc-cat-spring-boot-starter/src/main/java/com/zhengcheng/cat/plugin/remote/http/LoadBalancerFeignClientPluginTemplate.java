package com.zhengcheng.cat.plugin.remote.http;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.common.CatPluginConstants;
import com.zhengcheng.cat.plugin.remote.AbstractClientPluginTemplate;
import feign.Client;
import feign.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Description
 *
 * @author :    zhangquansheng
 * @date :    2019/12/10 9:49
 */
@Aspect
public class LoadBalancerFeignClientPluginTemplate extends AbstractClientPluginTemplate<Map<String, Collection<String>>> {

    private static final ThreadLocal<AtomicBoolean> Entered = new ThreadLocal<AtomicBoolean>() {
        @Override
        protected AtomicBoolean initialValue() {
            return new AtomicBoolean(false);
        }
    };

    @Pointcut("execution(public * feign.okhttp.OkHttpClient.execute(..))")
    public void scope() {
    }

    @Override
    @Around("scope()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object obj = null;
        boolean result = true;
        try {
            result = Entered.get().compareAndSet(false, true);
        } catch (Throwable t) {
        }
        if (result) {
            obj = super.doAround(pjp);
            try {
                Entered.get().set(false);
            } catch (Throwable tt) {
            }
        } else {
            obj = pjp.proceed();
        }
        return obj;

    }

    @Override
    public Transaction beginLog(ProceedingJoinPoint pjp) {
        Object target = pjp.getTarget();
        if (!(target instanceof Client)) {
            return null;
        }
        if (pjp.getArgs() != null || pjp.getArgs().length > 0 || pjp.getArgs()[0] instanceof feign.Request) {
            feign.Request request = (feign.Request) pjp.getArgs()[0];
            Map headers = request.headers();
            try {
                Transaction t = logTransaction(headers, new URI(request.url()), request.method());
                return t;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    private Transaction logTransaction(Map<String, Collection<String>> request, URI uri, String method) {
        Transaction transaction;
        // + getConcreteUri(uri.getPath())
        transaction = Cat.newTransaction(this.getTransactionType(), uri.getScheme() + "://" + uri.getAuthority());
        sendClientAddr(request, getClientAddrDataKey(), getClientAddrData());
        sendClientDomain(request, getClientDomainDataKey(), getClientDomainData());
        Cat.logEvent("Http.Method", method);
        logRemoteTrace(request);
        return transaction;
    }

    @Override
    public boolean enableTrace(Map<String, Collection<String>> headers) {
        addHeaderValue(headers, CatPluginConstants.D_CALL_TRACE_MODE, "trace");
        return true;
    }

    @Override
    public void endLog(Transaction transaction, Object retVal, Object... params) {
        Object[] addrs = ((Response) retVal).headers().get(getServerAddrDataKey()).toArray();
        Object[] domains = ((Response) retVal).headers().get(getServerDomainDataKey()).toArray();
        if (addrs != null && addrs.length == 1) {
            logServerAddr(String.valueOf(addrs[0]));
        }
        if (domains != null && domains.length == 1) {
            logServerDomain(String.valueOf(domains[0]));
        }
    }

    @Override
    public void logTrace(Map<String, Collection<String>> headers, Map.Entry<String, String> entry) {
        addHeaderValue(headers, entry.getKey(), entry.getValue());
    }

    @Override
    protected Map<String, Collection<String>> getRequestContext(ProceedingJoinPoint pjp) {
        return null;
    }

    @Override
    protected String getTransactionType() {
        return CatPluginConstants.TYPE_HTTP_CLIENT;
    }

    @Override
    protected String getTransactionName(Map<String, Collection<String>> headers) {
        return null;
    }

    @Override
    protected void sendClientAddr(Map<String, Collection<String>> headers, String key, String value) {
        addHeaderValue(headers, key, value);
    }

    @Override
    protected void sendClientDomain(Map<String, Collection<String>> headers, String key, String value) {
        addHeaderValue(headers, key, value);
    }

    @Override
    protected void specialHandling(Map<String, Collection<String>> headers) {

    }

    /**
     * headers中加入指定参数
     *
     * @param headers
     * @param key
     * @param value
     */
    private void addHeaderValue(Map<String, Collection<String>> headers, String key, String value) {
        if (headers.containsKey(key)) {
            headers.get(key).add(value);
        } else {
            List<String> tmp = new ArrayList<>();
            tmp.add(value);
            headers.put(key, tmp);
        }
    }
}
