package com.zhengcheng.core.feign.interceptor;

import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.dto.UserInfo;
import com.zhengcheng.common.holder.ZcUserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * TransmittableUserInfoFeighClientIntercepter
 *
 * @author quansheng1.zhang
 * @since 2022/7/4 14:28
 */
@Slf4j
@RequiredArgsConstructor
public class TransmittableUserInfoFeighClientIntercepter implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 安全设置要求信息: 判断设置请求头信息是否安全，主要解决不能把登录信息透传给第三方的问题
        //从应用上下文中取出user信息，放入Feign的请求头中
        UserInfo userInfo = ZcUserContextHolder.getUserInfo();
        if (Objects.nonNull(userInfo)) {
            requestTemplate.header(CommonConstants.HEADER_GATEWAY_UID, String.valueOf(userInfo.getId()));
            requestTemplate.header(CommonConstants.HEADER_GATEWAY_USER_NO, userInfo.getUserNo());
            requestTemplate.header(CommonConstants.HEADER_GATEWAY_USERNAME, userInfo.getUsername());
            requestTemplate.header(CommonConstants.HEADER_CLIENT_IP, userInfo.getClientIp());
        }
    }
}
