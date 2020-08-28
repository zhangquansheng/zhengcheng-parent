package com.zhengcheng.web.aspect;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Method;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 控制层日志打印
 *
 * @author :    quansheng.zhang
 * @date :    2019/11/28 16:04
 */
@Slf4j
@ConditionalOnClass(ObjectMapper.class)
@Aspect
public class ControllerLogAspect {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 定义拦截规则：
     * 有@RequestMapping注解的方法。
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        String pjpMethodInfo = this.getMethodInfo(pjp);
        log.info("{}{}", pjpMethodInfo, this.getRequestInfo(pjp));
        long beginTime = System.currentTimeMillis();
        Object retObj = pjp.proceed();
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("{} | {} | 耗时：{}ms", pjpMethodInfo, objectMapper.writeValueAsString(retObj), costMs);
        return retObj;
    }

    private String getMethodInfo(JoinPoint point) {
        StrBuilder sb = StrBuilder.create();
        String className = point.getSignature().getDeclaringType().getSimpleName();
        String methodName = point.getSignature().getName();
        sb.append(className).append(".").append(methodName);
        return sb.toString();
    }

    private String getRequestInfo(JoinPoint point) {
        StrBuilder sb = StrBuilder.create();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String method = request.getMethod();
            sb.append(" | method:[").append(method).append("]");
            sb.append(" | requestPath:[").append(request.getRequestURI()).append("]");
            if (Method.POST.toString().equalsIgnoreCase(method)) {
                Object[] args = point.getArgs();
                if (args.length > 0) {
                    sb.append(" | args:[");
                    for (Object arg : args) {
                        if (arg instanceof Serializable && !(arg instanceof MultipartFile)) {
                            sb.append(objectMapper.writeValueAsString(arg)).append(",");
                        }
                    }
                    sb.append("]");
                }
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (parameterMap != null && parameterMap.size() > 0) {
                    sb.append(" | param:[").append(objectMapper.writeValueAsString(parameterMap)).append("]");
                }
            } else {
                String queryString = request.getQueryString();
                if (StrUtil.isNotBlank(queryString)) {
                    sb.append(" | queryString:[").append(request.getQueryString()).append("]");
                }
            }
        } catch (Exception e) {
            sb.append("Exception:").append(e.getMessage());
        }
        return sb.toString();
    }
}
