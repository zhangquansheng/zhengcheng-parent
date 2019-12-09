package com.zhengcheng.cat.plugin.common;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.internal.AbstractMessage;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * CatUtils
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/9 20:29
 */
public class CatUtils {
    public CatUtils() {
    }

    public static RequestAttributes createMessageTree() {
        CatPropertyContext context = new CatPropertyContext();
        Cat.logRemoteCallClient(context, Cat.getManager().getDomain());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            requestAttributes.setAttribute("_catParentMessageId", context.getProperty("_catParentMessageId"), 0);
            requestAttributes.setAttribute("_catRootMessageId", context.getProperty("_catRootMessageId"), 0);
            requestAttributes.setAttribute("_catChildMessageId", context.getProperty("_catChildMessageId"), 0);
            requestAttributes.setAttribute("application.name", Cat.getManager().getDomain(), 0);
        }

        return requestAttributes;
    }

    public static void createConsumerCross(Transaction transaction, String callApp, String callServer, String callPort) {
        Event crossOwnerEvent = Cat.newEvent("RemoteCall.ownerIp", getLocalHost());
        Event crossAppEvent = Cat.newEvent("RemoteCall.providerApp", callApp);
        Event crossServerEvent = Cat.newEvent("RemoteCall.providerServer", callServer);
        Event crossPortEvent = Cat.newEvent("RemoteCall.providerPort", callPort);
        crossOwnerEvent.setStatus("0");
        crossAppEvent.setStatus("0");
        crossServerEvent.setStatus("0");
        crossPortEvent.setStatus("0");
        completeEvent(crossOwnerEvent);
        completeEvent(crossAppEvent);
        completeEvent(crossPortEvent);
        completeEvent(crossServerEvent);
        transaction.addChild(crossOwnerEvent);
        transaction.addChild(crossAppEvent);
        transaction.addChild(crossPortEvent);
        transaction.addChild(crossServerEvent);
    }

    public static void completeEvent(Event event) {
        AbstractMessage message = (AbstractMessage) event;
        message.setCompleted(true);
    }

    public static String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
            return "";
        }
    }
}
