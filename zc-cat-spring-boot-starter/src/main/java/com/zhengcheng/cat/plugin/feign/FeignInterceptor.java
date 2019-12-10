package com.zhengcheng.cat.plugin.feign;

import com.dianping.cat.Cat;
import com.zhengcheng.cat.plugin.common.CatMsgConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * FeignInterceptor
 *
 * @author :    zhangquansheng
 * @date :    2019/12/10 9:21
 */
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String rootId = requestAttributes.getAttribute(Cat.Context.ROOT, 0).toString();
        String childId = requestAttributes.getAttribute(Cat.Context.CHILD, 0).toString();
        String parentId = requestAttributes.getAttribute(Cat.Context.PARENT, 0).toString();
        requestTemplate.header(Cat.Context.ROOT, rootId);
        requestTemplate.header(Cat.Context.CHILD, childId);
        requestTemplate.header(Cat.Context.PARENT, parentId);
        requestTemplate.header(CatMsgConstants.APPLICATION_KEY, Cat.getManager().getDomain());
        if (log.isDebugEnabled()) {
            log.debug(" 开始Feign远程调用 : " + requestTemplate.method() + " 消息模型 : rootId = " + rootId + " parentId = " + parentId + " childId = " + childId);
        }
    }
}
