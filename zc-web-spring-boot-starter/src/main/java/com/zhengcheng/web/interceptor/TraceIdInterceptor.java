package com.zhengcheng.web.interceptor;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 路径拦截器
 *
 * @author :    zhangquansheng
 * @date :    2020/3/24 13:55
 */
@Slf4j
public class TraceIdInterceptor implements HandlerInterceptor {

    private String applicationName;

    public TraceIdInterceptor(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception{
        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String uri = request.getRequestURI();
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        String remoteIp = request.getRemoteAddr();
        String traceId = request.getHeader(CommonConstants.TRACE_ID);
        if (StrUtil.isBlankOrUndefined(traceId)) {
            traceId = IdUtil.fastSimpleUUID();
            request.setAttribute(CommonConstants.TRACE_ID, traceId);
        }
        MDC.put(CommonConstants.TRACE_ID, traceId);
        log.info("applicationName:[{}], clientIp:[{}], X-Forwarded-For:[{}]", applicationName, remoteIp, xForwardedForHeader);
        return true;
    }

}
