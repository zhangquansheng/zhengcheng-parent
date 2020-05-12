package com.zhengcheng.web.aspect;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * 控制层日志打印
 *
 * @author :    quansheng.zhang
 * @date :    2019/11/28 16:04
 */
@Slf4j
@Aspect
@Component
public class ControllerLogAspect {

    /**
     * 定义拦截规则：
     * 有@RequestMapping注解的方法。
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("请求开始[{}]", this.getMethodInfo(pjp));
        long beginTime = System.currentTimeMillis();
        Object retObj = pjp.proceed();
        long costMs = System.currentTimeMillis() - beginTime;
        if (retObj instanceof Result) {
            log.info("返回结果:[{}]，耗时：{}ms", JSONUtil.toJsonStr(retObj), costMs);
        }
        return retObj;
    }

    private String getMethodInfo(JoinPoint point) {
        StrBuilder sb = StrBuilder.create();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String method = request.getMethod();
            String className = point.getSignature().getDeclaringType().getSimpleName();
            String methodName = point.getSignature().getName();
            sb.append(className).append(".").append(methodName);
            if (Method.POST.toString().equalsIgnoreCase(method)) {
                Object[] args = point.getArgs();
                if (args.length > 0) {
                    if (args[0] instanceof Serializable && !(args[0] instanceof MultipartFile)) {
                        sb.append(" | args:").append(JSONUtil.toJsonStr(args[0]));
                    }
                }
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (parameterMap != null && parameterMap.size() > 0) {
                    sb.append(" | param:").append(JSONUtil.toJsonStr(parameterMap));
                }
            } else {
                String queryString = request.getQueryString();
                if (StrUtil.isNotBlank(queryString)) {
                    sb.append(" | param:").append(queryString);
                }
            }
        } catch (Exception e) {
            sb.append("|Exception:").append(e.getMessage());
        }
        return sb.toString();
    }
}
