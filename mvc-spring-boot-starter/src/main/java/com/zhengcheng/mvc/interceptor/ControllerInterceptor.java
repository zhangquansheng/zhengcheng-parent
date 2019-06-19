package com.zhengcheng.mvc.interceptor;

import com.alibaba.fastjson.JSON;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.nutz.lang.Lang;
import org.springframework.stereotype.Component;


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
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Object[] args = pjp.getArgs();
        String argsJsonStr = "";
        if (args != null) {
            argsJsonStr = JSON.toJSONString(args);
        }
        Object retObj;
        try {
            retObj = pjp.proceed();
        } catch (Throwable e) {
            if (e instanceof BizException) {
                log.info("BizException#{}#{}#{}请求异常，错误：{}", className, methodName, argsJsonStr, e.getMessage());
                retObj = Result.errorMessage(e.getMessage());
            } else {
                log.error("{}#{}#{}请求异常，错误：{}", className, methodName, argsJsonStr, e.getMessage(), e);
                retObj = Result.errorData("服务异常，请联系技术人员", Lang.getStackTrace(e));
            }
        }
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("{}#{}#{}请求结束，耗时：{}ms", className, methodName, argsJsonStr, costMs);
        return retObj;
    }
}