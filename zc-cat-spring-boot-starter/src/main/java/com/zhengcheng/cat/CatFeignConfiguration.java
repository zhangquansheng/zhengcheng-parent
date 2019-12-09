package com.zhengcheng.cat;

import com.dianping.cat.Cat;
import com.zhengcheng.cat.plugin.feign.CatConstantsExt;
import com.zhengcheng.cat.plugin.feign.CatContextImpl;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

/**
 * 适用于使用feign调用其他SpringCloud微服务的调用链上下文传递场景
 * 作用：在使用feign请求其他微服务时，自动生成context上下文，并将相应参数rootId、parentId、childId放入header
 * 使用方法：在需要添加catcontext的feign service接口中，@FeignClient注解添加此类的configuration配置，
 * 如：@FeignClient(name="account-manage", configuration = CatFeignConfiguration.class)
 *
 * @author soar
 * @date 2019-01-10
 */
@Slf4j
@Configuration
@ConditionalOnClass({FeignClient.class})
public class CatFeignConfiguration implements RequestInterceptor {

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
