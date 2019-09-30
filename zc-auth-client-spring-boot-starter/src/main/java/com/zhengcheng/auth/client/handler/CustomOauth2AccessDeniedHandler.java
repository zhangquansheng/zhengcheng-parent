package com.zhengcheng.auth.client.handler;

import com.zhengcheng.auth.client.util.ResponseUtil;
import com.zhengcheng.common.enumeration.CodeEnum;
import com.zhengcheng.common.web.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义Auth2AccessDeniedHandler
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/24 11:13
 */
public class CustomOauth2AccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtil.responseWriter(httpServletResponse, HttpStatus.FORBIDDEN.value(), Result.create(CodeEnum.FORBIDDEN.getCode(), e.getMessage()));
    }
}
