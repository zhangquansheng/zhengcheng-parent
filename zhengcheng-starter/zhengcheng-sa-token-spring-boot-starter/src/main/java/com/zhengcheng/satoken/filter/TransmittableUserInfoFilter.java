package com.zhengcheng.satoken.filter;


import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.satoken.domain.UserInfo;
import com.zhengcheng.satoken.holder.ZcUserContextHolder;

import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TransmittableUserInfoFilter
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 19:35
 */
@Slf4j
@RequiredArgsConstructor
public class TransmittableUserInfoFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        try {
            String uid = request.getHeader(CommonConstants.HEADER_GATEWAY_UID);
            String userNo = request.getHeader(CommonConstants.HEADER_GATEWAY_USER_NO);
            String username = request.getHeader(CommonConstants.HEADER_GATEWAY_USERNAME);
            String clientIp = request.getHeader(CommonConstants.HEADER_CLIENT_IP);

            UserInfo userInfo = new UserInfo();
            userInfo.setId(CharSequenceUtil.isBlank(uid) ? null : Long.valueOf(uid));
            userInfo.setUserNo(userNo);
            userInfo.setUsername(username);
            userInfo.setClientIp(clientIp);

            //将UserInfo放入上下文中
            ZcUserContextHolder.setUserInfo(userInfo);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ZcUserContextHolder.removeUserInfo();
        }
    }

}
