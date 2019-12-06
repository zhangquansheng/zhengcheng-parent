package com.zhengcheng.cat.plugin.remote.http;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.zhengcheng.cat.plugin.constant.CatPluginConstants;
import com.zhengcheng.cat.plugin.remote.ClientPluginTemplate;
import feign.Client;
import feign.Response;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * OkHttpClientPluginTemplate
 *
 * @author :    zhangquansheng
 * @date :    2019/12/6 10:24
 */
@Slf4j
@ConditionalOnClass({OkHttpClient.class})
@Aspect
public class OkHttpClientPluginTemplate extends ClientPluginTemplate<Map<String, Collection<String>>> {

    private static final ThreadLocal<AtomicBoolean> ENTERED = ThreadLocal.withInitial(() -> new AtomicBoolean(false));

    @Override
    @Around("execution(* feign.okhttp.OkHttpClient.execute(..))")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object obj;
        boolean result = true;
        try {
            result = ENTERED.get().compareAndSet(false, true);
        } catch (Throwable t) {
            log.error("feign OkHttpClient compareAndSet false Exception message:{}", t.getMessage(), t);
        }
        if (result) {
            obj = super.doAround(pjp);
            try {
                ENTERED.get().set(false);
            } catch (Throwable tt) {
                log.error("feign OkHttpClient set false Exception message:{}", tt.getMessage(), tt);
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
            Map<String, Collection<String>> headers = request.headers();
            try {
                return logTransaction(headers, new URI(request.url()), request.method());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    private Transaction logTransaction(Map<String, Collection<String>> request, URI uri, String method) {
        Transaction transaction;
        transaction = Cat.newTransaction(this.getTransactionType(), uri.getScheme() + "://" + uri.getAuthority() + uri.getPath());
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
        Object[] adders = ((Response) retVal).headers().get(getServerAddrDataKey()).toArray();
        Object[] domains = ((Response) retVal).headers().get(getServerDomainDataKey()).toArray();
        if (adders.length == 1) {
            logServerAddr(String.valueOf(adders[0]));
        }
        if (domains.length == 1) {
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
