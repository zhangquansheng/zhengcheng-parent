package com.zhengcheng.core.cat;

import com.dianping.cat.Cat;
import com.zhengcheng.core.cat.context.CatMsgConstants;
import com.zhengcheng.core.cat.context.CatMsgContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
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
@Configuration
@ConditionalOnClass({RequestInterceptor.class, FeignClient.class})
public class CatFeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        CatMsgContext catContext = new CatMsgContext();
        Cat.logRemoteCallClient(catContext, Cat.getManager().getDomain());
        requestTemplate.header(CatMsgConstants.CAT_HTTP_HEADER_ROOT_MESSAGE_ID, catContext.getProperty(Cat.Context.ROOT));
        requestTemplate.header(CatMsgConstants.CAT_HTTP_HEADER_PARENT_MESSAGE_ID, catContext.getProperty(Cat.Context.PARENT));
        requestTemplate.header(CatMsgConstants.CAT_HTTP_HEADER_CHILD_MESSAGE_ID, catContext.getProperty(Cat.Context.CHILD));
        requestTemplate.header(CatMsgConstants.APPLICATION_KEY, Cat.getManager().getDomain());
    }
}
