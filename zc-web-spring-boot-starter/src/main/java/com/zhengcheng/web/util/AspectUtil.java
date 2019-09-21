package com.zhengcheng.web.util;

import com.alibaba.fastjson.JSON;
import com.zhengcheng.common.enumeration.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * AspectUtil
 *
 * @author :    quansheng.zhang
 * @date :    2019/6/26 13:29
 */
@Slf4j
public class AspectUtil {

    /**
     * 拦截器:
     * 记录请求日志
     * 拦截异常
     *
     * @param pjp
     * @return Object 被拦截方法的执行结果
     */
    public Object proceed(ProceedingJoinPoint pjp) {
        long beginTime = System.currentTimeMillis();
        Object retObj;
        try {
            retObj = pjp.proceed();
        } catch (Throwable e) {
            if (e instanceof BizException) {
                retObj = Result.create(((BizException) e).getCode(), e.getMessage());
                log.info("{}请求异常，错误：{}", this.getMethodInfo(pjp), e.getMessage());
            } else {
                retObj = Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
                log.error("{}请求异常，错误：{}", this.getMethodInfo(pjp), e.getMessage(), e);
            }
        }
        long costMs = System.currentTimeMillis() - beginTime;
        if (retObj instanceof Result) {
            log.info("{}请求结束，耗时：{}ms", this.getMethodInfo(pjp), costMs);
        }
        return retObj;
    }

    private String getMethodInfo(JoinPoint point) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String method = request.getMethod();
            String className = point.getSignature().getDeclaringType().getSimpleName();
            String methodName = point.getSignature().getName();
            sb.append(className).append(".").append(methodName);
            if ("POST".equalsIgnoreCase(method)) {
                Object[] args = point.getArgs();
                if (args.length > 0) {
                    if (args[0] instanceof Serializable) {
                        sb.append(" | args:").append(JSON.toJSONString(args[0]));
                    }
                }
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (parameterMap != null && parameterMap.size() > 0) {
                    sb.append(" | param:").append(JSON.toJSONString(parameterMap));
                }
            } else {
                sb.append(" | param:").append(request.getQueryString());
            }
        } catch (Exception e) {
            sb.append("|Exception:").append(e.getMessage());
        }
        return sb.toString();
    }
}
