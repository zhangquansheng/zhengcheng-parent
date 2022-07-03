package com.zhengcheng.core.filter;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.dto.UserInfo;
import com.zhengcheng.common.holder.ZcUserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * TransmittableUserInfoFilter
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 19:35
 */
@Slf4j
@RequiredArgsConstructor
public class TransmittableUserInfoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uid = request.getHeader(CommonConstants.HEADER_GATEWAY_UID);
        String userNo = request.getHeader(CommonConstants.HEADER_GATEWAY_USER_NO);
        String username = request.getHeader(CommonConstants.HEADER_GATEWAY_USERNAME);
        String clientIp = request.getHeader(CommonConstants.HEADER_CLIENT_IP);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(StrUtil.isBlank(uid) ? null : Long.valueOf(uid));
        userInfo.setUserNo(userNo);
        userInfo.setUsername(username);
        userInfo.setClientIp(clientIp);

        //将UserInfo放入上下文中
        ZcUserContextHolder.setUserInfo(userInfo);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        ZcUserContextHolder.removeUserInfo();
    }
}
