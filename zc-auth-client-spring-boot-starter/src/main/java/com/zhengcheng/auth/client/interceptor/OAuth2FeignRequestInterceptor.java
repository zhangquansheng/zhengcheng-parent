package com.zhengcheng.auth.client.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.constant.CommonConstants;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * feign client将access token放入请求头里
 *
 * @author :    qunsheng.zhang
 * @date :    2019/3/24 13:57
 */
@Slf4j
@ConditionalOnClass({RequestInterceptor.class, FeignClient.class})
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_TOKEN_TYPE = "Bearer";

    @Override
    public void apply(feign.RequestTemplate requestTemplate) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            String value = String.format("%s %s", BEARER_TOKEN_TYPE, details.getTokenValue());
            requestTemplate.header(AUTHORIZATION_HEADER, value);
            if (log.isDebugEnabled()) {
                log.debug("put feign header  [{}] = [{}]", BEARER_TOKEN_TYPE, value);
            }

            String traceId = MDC.get(CommonConstants.TRACE_ID);
            if (StrUtil.isEmptyOrUndefined(traceId)) {
                // 一些接口的调用需要实现幂等，比如消息发送，如果使用requestId就可以方便服务方实现幂等
                requestTemplate.header(CommonConstants.TRACE_ID, IdUtil.fastSimpleUUID());
            } else {
                requestTemplate.header(CommonConstants.TRACE_ID, traceId);
            }
        }
    }
}
