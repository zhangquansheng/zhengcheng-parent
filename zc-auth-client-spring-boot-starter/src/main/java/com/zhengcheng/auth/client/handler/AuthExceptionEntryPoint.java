package com.zhengcheng.auth.client.handler;

import com.zhengcheng.auth.client.util.ResponseUtil;
import com.zhengcheng.common.enumeration.CodeEnum;
import com.zhengcheng.common.web.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义AuthExceptionEntryPoint用于token校验失败返回信息
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/24 11:23
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.responseWriter(httpServletResponse, HttpStatus.UNAUTHORIZED.value(), Result.create(CodeEnum.UNAUTHORIZED.getCode(), CodeEnum.UNAUTHORIZED.getMessage(), e.getMessage()));
    }
}
