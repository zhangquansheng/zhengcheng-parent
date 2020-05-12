package com.zhengcheng.web.aspect;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.web.CodeEnum;
import com.zhengcheng.redis.annotation.RequestLimit;
import com.zhengcheng.web.util.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Objects;

/**
 * 访问接口限流
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:15
 */
@Slf4j
@Aspect
@Component
@ConditionalOnClass({DefaultRedisScript.class, StringRedisTemplate.class})
public class RequestLimitAspect {

    @Autowired
    private DefaultRedisScript<Boolean> redisScript;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.zhengcheng.redis.annotation.RequestLimit)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();

        try {

            // 获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息
            Signature signature = joinPoint.getSignature();
            // 获取传入目标方法的参数对象
            Object[] args = joinPoint.getArgs();

            //拦截的方法名称
            String methodName = signature.getName();
            //拦截的放参数类型
            Class[] parameterTypes = ((MethodSignature) signature).getMethod().getParameterTypes();
            Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            // 必须要用AnnotationUtils，才能获取到 name 和 value上@AliasFor(互为别名)的作用
            // AOP原理
            RequestLimit requestLimit = AnnotationUtils.findAnnotation(method, RequestLimit.class);

            String name = requestLimit.name();
            if (StrUtil.isBlank(name)) {
                // 获取当前方法名和参数
                name = method.toGenericString().replaceAll(" ", "");
            }
            String ip = IpAddressUtils.getIpAddress(request);
            String key = StrUtil.format("{}{}:{}", CommonConstants.REQUEST_LIMIT_KEY_PREFIX, name, ip);
            if (log.isDebugEnabled()) {
                log.debug("限流接口的KEY:[{}]", key);
            }

            Boolean allow = stringRedisTemplate.execute(
                    redisScript,
                    Collections.singletonList(key),
                    String.valueOf(requestLimit.count()), //limit
                    String.valueOf(requestLimit.time())); //expire

            if (Objects.equals(Boolean.FALSE, allow)) {
                throw new BizException(CodeEnum.REQUEST_EXCEED_LIMIT);
            }
        } catch (NoSuchMethodException e) {
            log.error("{}", e.getMessage(), e);
        }
    }
}
