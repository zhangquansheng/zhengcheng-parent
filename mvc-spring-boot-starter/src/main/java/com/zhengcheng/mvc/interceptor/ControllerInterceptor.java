package com.zhengcheng.mvc.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.nutz.lang.Lang;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;


/**
 * 控制层拦截器
 *
 * @author :    quansheng.zhang
 * @Filename :     ControllerInterceptor.java
 * @Package :     com.zhengcheng.config
 * @Description :
 * @date :    2019/1/26 7:59
 */
@Slf4j
@Aspect
@Component
public class ControllerInterceptor {

    /**
     * 定义拦截规则：
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    /**
     * 拦截器:
     * 记录请求日志
     * 拦截异常
     *
     * @param pjp
     * @return Object 被拦截方法的执行结果
     */
    @Around("controllerMethodPointcut()")
    public Object interceptor(ProceedingJoinPoint pjp) {
        long beginTime = System.currentTimeMillis();
        Object retObj;
        try {
            retObj = pjp.proceed();
        } catch (Throwable e) {
            if (e instanceof BizException) {
                retObj = Result.errorMessage(e.getMessage());
                log.info("{}请求异常，错误：{}", this.getMethodInfo(pjp), e.getMessage());
            } else {
                retObj = Result.errorData("服务异常，请联系技术人员", Lang.getStackTrace(e));
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