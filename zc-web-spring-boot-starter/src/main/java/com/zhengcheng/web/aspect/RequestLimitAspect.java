package com.zhengcheng.web.aspect;

import cn.hutool.core.util.StrUtil;
import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.web.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.redis.annotation.RequestLimit;
import com.zhengcheng.web.util.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
public class RequestLimitAspect {

    @Autowired
    private DefaultRedisScript<Boolean> redisScript;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.zhengcheng.redis.annotation.RequestLimit)")
    public void pointcut() {
    }

    @Before("pointcut() && @annotation(requestLimit)")
    public void doBefore(JoinPoint joinPoint, RequestLimit requestLimit) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();

        String ip = IpAddressUtils.getIpAddress(request);
        String key = StrUtil.format("{}{}", CommonConstants.REQUEST_LIMIT_KEY_PREFIX, ip);

        Boolean allow = stringRedisTemplate.execute(
                redisScript,
                Collections.singletonList(key),
                String.valueOf(requestLimit.count()), //limit
                String.valueOf(requestLimit.time())); //expire

        if (Objects.equals(Boolean.FALSE, allow)) {
            throw new BizException(CodeEnum.REQUEST_EXCEED_LIMIT);
        }
    }
}
