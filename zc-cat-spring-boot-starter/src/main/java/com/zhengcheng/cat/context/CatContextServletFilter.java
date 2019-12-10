package com.zhengcheng.cat.context;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.internal.AbstractMessage;
import com.dianping.cat.message.internal.NullMessage;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * http协议传输，远程调用链目标端接收context的filter，
 * 通过header接收rootId、parentId、childId并放入CatContextImpl中，调用Cat.logRemoteCallServer()进行调用链关联
 * 注:若不涉及调用链，则直接使用cat-client.jar中提供的filter即可
 * 使用方法（视项目框架而定）：
 * 1、web项目：在web.xml中引用此filter
 * 2、Springboot项目，通过注入bean的方式注入此filter
 *
 * @author soar
 * @date 2019-01-10
 */
@Slf4j
public class CatContextServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        Transaction filterTransaction;
        //若header中有context相关属性，则生成调用链，若无，仅统计请求的Transaction
        if (null != request.getHeader(CatMsgConstants.CAT_HTTP_HEADER_ROOT_MESSAGE_ID)) {
            filterTransaction = Cat.newTransaction(CatConstants.TYPE_URL_FORWARD, uri);

            CatMsgContext catContext = new CatMsgContext();
            catContext.addProperty(Cat.Context.ROOT, request.getHeader(CatMsgConstants.CAT_HTTP_HEADER_ROOT_MESSAGE_ID));
            catContext.addProperty(Cat.Context.PARENT, request.getHeader(CatMsgConstants.CAT_HTTP_HEADER_PARENT_MESSAGE_ID));
            catContext.addProperty(Cat.Context.CHILD, request.getHeader(CatMsgConstants.CAT_HTTP_HEADER_CHILD_MESSAGE_ID));
            Cat.logRemoteCallServer(catContext);
            this.createProviderCross(request, filterTransaction);

        } else {
            filterTransaction = Cat.newTransaction(CatConstants.TYPE_URL, uri);
        }

        try {
            Cat.logEvent(CatMsgConstants.TYPE_URL_METHOD, request.getMethod(), Message.SUCCESS, request.getRequestURL().toString());
            Cat.logEvent(CatMsgConstants.TYPE_URL_CLIENT, request.getRemoteHost());

            filterChain.doFilter(servletRequest, servletResponse);

            filterTransaction.setSuccessStatus();
        } catch (Exception e) {
            filterTransaction.setStatus(e);
            Cat.logError(e);
        } finally {
            filterTransaction.complete();
        }
    }

    @Override
    public void destroy() {

    }


    /**
     * 串联provider端消息树
     */
    private void createProviderCross(HttpServletRequest request, Transaction t) {
        Event crossAppEvent = Cat.newEvent(CatMsgConstants.PROVIDER_CALL_APP, request.getHeader(CatMsgConstants.APPLICATION_KEY));
        Event crossServerEvent = Cat.newEvent(CatMsgConstants.PROVIDER_CALL_SERVER, request.getRemoteAddr());
        crossAppEvent.setStatus(Event.SUCCESS);
        crossServerEvent.setStatus(Event.SUCCESS);
        completeEvent(crossAppEvent);
        completeEvent(crossServerEvent);
        t.addChild(crossAppEvent);
        t.addChild(crossServerEvent);
    }

    private void completeEvent(Event event) {
        if (event != NullMessage.EVENT) {
            AbstractMessage message = (AbstractMessage) event;
            message.setCompleted(true);
        }
    }

}
