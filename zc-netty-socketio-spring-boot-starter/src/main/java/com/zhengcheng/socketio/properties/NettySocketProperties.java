package com.zhengcheng.socketio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * NettySocketProperties
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "rt-server")
public class NettySocketProperties {
    private String host = "localhost";
    private Integer port = 9092;
    private Integer pingInterval = 300000;
    private Integer upgradeTimeout = 25000;
    private Integer pingTimeout = 60000;
    private String token;
    private boolean randomSession = true;
    private RedissonProperties redisson;
}
