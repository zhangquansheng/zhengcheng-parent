package com.zhengcheng.cache.ratelimit.handler;


import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.utils.IpAddressUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * DefaultLimitKeyHandler
 *
 * @author :    zhangquansheng
 * @date :    2020/5/12 16:15
 */
public class DefaultLimitKeyHandler implements LimitKeyHandler {
    /**
     * 使用用户为key 必须重写
     * 否则抛出异常
     */
    @Override
    public String getUserKey() {
        throw new BizException("使用用户为限流KEY，必须重写LimitKeyHandler！");
    }

    @Override
    public String getIpKey() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (null != requestAttributes) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return Objects.isNull(request) ? "" : IpAddressUtil.getIpAddress(request);
    }
}
