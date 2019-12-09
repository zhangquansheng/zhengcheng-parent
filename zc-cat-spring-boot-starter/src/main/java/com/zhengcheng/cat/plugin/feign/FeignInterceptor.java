package com.zhengcheng.cat.plugin.feign;

import com.dianping.cat.Cat;
import com.zhengcheng.cat.plugin.http.CatConstantsExt;
import com.zhengcheng.cat.plugin.http.CatContextImpl;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Description
 *
 * @author :    zhangquansheng
 * @date :    2019/12/9 14:09
 */
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        CatContextImpl catContext = new CatContextImpl();
        Cat.logRemoteCallClient(catContext, Cat.getManager().getDomain());
        String rootId = catContext.getProperty(Cat.Context.ROOT);
        String parentId = catContext.getProperty(Cat.Context.PARENT);
        String childId = catContext.getProperty(Cat.Context.CHILD);
        requestTemplate.header(CatConstantsExt.CAT_HTTP_HEADER_ROOT_MESSAGE_ID, rootId);
        requestTemplate.header(CatConstantsExt.CAT_HTTP_HEADER_PARENT_MESSAGE_ID, parentId);
        requestTemplate.header(CatConstantsExt.CAT_HTTP_HEADER_CHILD_MESSAGE_ID, childId);
        if (log.isDebugEnabled()) {
            log.debug(" 开始Feign远程调用 : " + requestTemplate.method() + " 消息模型 : rootId = " + rootId + " parentId = " + parentId + " childId = " + childId);
        }
    }


}
