package com.zhengcheng.web.interceptor;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.zhengcheng.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 防篡改、防重放攻击 拦截器
 *
 * @author :    quansheng.zhang
 * @date :    2020/4/18 17:33
 */
@Slf4j
public class SignAuthInterceptor implements HandlerInterceptor {

    private RedisTemplate<String, String> redisTemplate;

    private String key;

    public SignAuthInterceptor(RedisTemplate<String, String> redisTemplate, String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 获取时间戳
        String timestamp = request.getHeader("timestamp");
        // 获取随机字符串
        String nonceStr = request.getHeader("nonceStr");
        // 获取签名
        String signature = request.getHeader("signature");

        // 判断时间是否大于xx秒(防止重放攻击)
        long NONCE_STR_TIMEOUT_SECONDS = 60L;
        if (StrUtil.isEmpty(timestamp) || DateUtil.between(DateUtil.date(Long.parseLong(timestamp) * 1000), DateUtil.date(), DateUnit.SECOND) > NONCE_STR_TIMEOUT_SECONDS) {
            throw new BizException("invalid  timestamp");
        }

        // 判断该用户的nonceStr参数是否已经在redis中（防止短时间内的重放攻击）
        Boolean haveNonceStr = redisTemplate.hasKey(nonceStr);
        if (StrUtil.isEmpty(nonceStr) || Objects.isNull(haveNonceStr) || haveNonceStr) {
            throw new BizException("invalid nonceStr");
        }

        // 对请求头参数进行签名
        if (StrUtil.isEmpty(signature) || !Objects.equals(signature, this.signature(timestamp, nonceStr, request))) {
            throw new BizException("invalid signature");
        }

        // 将本次用户请求的nonceStr参数存到redis中设置xx秒后自动删除
        redisTemplate.opsForValue().set(nonceStr, nonceStr, NONCE_STR_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        return true;
    }

    private String signature(String timestamp, String nonceStr, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>(16);
        Enumeration<String> enumeration = request.getParameterNames();
        if (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getParameter(name);
            params.put(name, value);
        }
        String qs = this.sortQueryParamString(params);
        return SecureUtil.md5(String.format("%s&timestamp=%s&nonceStr=%s&key=%s", qs, timestamp, nonceStr, key)).toLowerCase();
    }

    /**
     * 按照字母顺序进行升序排序
     *
     * @param params 请求参数 。注意请求参数中不能包含key
     * @return 排序后结果
     */
    private String sortQueryParamString(Map<String, Object> params) {
        List<String> listKeys = Lists.newArrayList(params.keySet());
        Collections.sort(listKeys);
        StrBuilder content = StrBuilder.create();
        for (String param : listKeys) {
            content.append(param).append("=").append(params.get(param).toString()).append("&");
        }
        if (content.length() > 0) {
            return content.subString(0, content.length() - 1);
        }
        return content.toString();
    }
}
