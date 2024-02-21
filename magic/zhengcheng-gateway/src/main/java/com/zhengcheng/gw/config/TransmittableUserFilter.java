package com.zhengcheng.gw.config;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.gw.feign.dto.UserDTO;
import com.zhengcheng.gw.service.UserInfoService;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * TransmitUserFilter
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 8:59
 */
@Slf4j
@Component
public class TransmittableUserFilter implements GlobalFilter, Ordered {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        ServerHttpRequest.Builder builder = request.mutate();
        builder.header(CommonConstants.HEADER_CLIENT_IP, getIpAddress(request));

        String token = exchange.getRequest().getHeaders().getFirst("satoken");
        if (CharSequenceUtil.isNotBlank(token)) {
            Future<UserDTO> userFuture = userInfoService.getUserInfoByToken(token);
            try {
                UserDTO userDTO = userFuture.get(10, TimeUnit.SECONDS);
                builder.header(CommonConstants.HEADER_GATEWAY_UID, String.valueOf(userDTO.getId()));
                builder.header(CommonConstants.HEADER_GATEWAY_USER_NO, userDTO.getUserNo());
                builder.header(CommonConstants.HEADER_GATEWAY_USERNAME, userDTO.getUsername());
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error("TransmittableUserFilter getUserInfoByToken Exception Message : {}", e.getMessage(), e);
            }
        }
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * ServerHttpRequest获取用户真实IP
     * @param request ServerHttpRequest
     * @return 用户真实IP
     */
    public static String getIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return ip;
    }
}
