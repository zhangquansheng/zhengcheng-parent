package com.zhengcheng.ums.common.interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.dto.UserInfo;
import com.zhengcheng.common.holder.ZcUserContextHolder;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.ums.controller.facade.UserFacade;
import com.zhengcheng.ums.dto.UserDTO;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;

/**
 * Interceptor - 用户拦截器
 *
 * @author :    zhngquansheng
 * @date :    2019/12/20 15:17
 */
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserFacade userFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String clientIp = request.getHeader(CommonConstants.HEADER_CLIENT_IP);
        UserDTO userDTO = userFacade.findById(StpUtil.getLoginIdAsLong());
        if (Objects.nonNull(userDTO)) {
            UserInfo userInfo = BeanUtil.copyProperties(userDTO, UserInfo.class);
            userInfo.setClientIp(clientIp);
            ZcUserContextHolder.setUserInfo(userInfo);
            return true;
        }

        response.setCharacterEncoding("UTF-8");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Result<String> result = Result.error();
        response.getWriter().println(JSONUtil.toJsonStr(result));
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        ZcUserContextHolder.removeUserInfo();
    }

}
