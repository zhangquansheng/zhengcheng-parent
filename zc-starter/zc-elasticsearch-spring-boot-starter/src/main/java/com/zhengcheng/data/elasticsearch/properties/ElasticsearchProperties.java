package com.zhengcheng.data.elasticsearch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * ElasticsearchProperties
 *
 * @author quansheng1.zhang
 * @since 2021/6/16 19:33
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {

    private String host = "127.0.0.1:9200";
    private String username;
    private String password;
    /**
     * 连接池里的最大连接数
     */
    private Integer maxConnectTotal = 30;

    /**
     * 某一个/每服务每次能并行接收的请求数量
     */
    private Integer maxConnectPerRoute = 10;

    /**
     * http clilent中从connetcion pool中获得一个connection的超时时间
     */
    private Integer connectionRequestTimeoutMillis = 2000;

    /**
     * 响应超时时间，超过此时间不再读取响应
     */
    private Integer socketTimeoutMillis = 30000;

    /**
     * 链接建立的超时时间
     */
    private Integer connectTimeoutMillis = 2000;

}
