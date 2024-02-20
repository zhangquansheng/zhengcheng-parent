package com.zhengcheng.gw.swagger.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * 在集成Spring Cloud Gateway网关的时候,会出现没有basePath的情况(即定义的例如/user、/order等微服务的前缀),
 * 这个情况在使用zuul网关的时候不会出现此问题,因此,在Gateway网关需要添加一个Filter实体Bean
 *
 * @author quansheng1.zhang
 * @since 2022/5/28 12:42
 */
@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory<Object> {

    private static final String HEADER_NAME = "X-Forwarded-Prefix";

    private static final String URI = "/v2/api-docs";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (!StringUtils.endsWithIgnoreCase(path, URI)) {
                return chain.filter(exchange);
            }
            String basePath = path.substring(0, path.lastIndexOf(URI));
            ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newExchange);
        };
    }
}
