package com.zhengcheng.auth.client.interceptor;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
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
        }
    }
}
