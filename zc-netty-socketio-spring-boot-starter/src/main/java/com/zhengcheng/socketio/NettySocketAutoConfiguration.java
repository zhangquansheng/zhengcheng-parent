package com.zhengcheng.socketio;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.zhengcheng.socketio.properties.NettySocketProperties;
import com.zhengcheng.socketio.runner.SocketIOServerCommandLineRunner;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * NettySocket自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/18 21:31
 */
@Configuration
@Import({SocketIOServerCommandLineRunner.class})
@EnableConfigurationProperties({NettySocketProperties.class})
public class NettySocketAutoConfiguration {

    /**
     * token参数
     */
    public static final String URL_PARAM_TOKEN = "token";

    @Bean
    public SocketIOServer socketIOServer(NettySocketProperties nettySocketProperties) {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setOrigin(nettySocketProperties.getOrigin());
        config.setHostname(nettySocketProperties.getHost());
        config.setPort(nettySocketProperties.getPort());
        if (nettySocketProperties.getRedisson().isEnable()) {
            org.redisson.config.Config redissonConfig = new org.redisson.config.Config();
            // 去官网看集群版 https://github.com/redisson/redisson#quick-start
            String address = "redis://".concat(nettySocketProperties.getRedisson().getHost()).concat(":").concat(nettySocketProperties.getRedisson().getPort());
            redissonConfig.useSingleServer().setPassword(nettySocketProperties.getRedisson().getPassword()).setAddress(address);
            RedissonClient redisson = Redisson.create(redissonConfig);
            RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(redisson);
            config.setStoreFactory(redisStoreFactory);
        }
        config.setRandomSession(nettySocketProperties.isRandomSession());
        config.setPingInterval(nettySocketProperties.getPingInterval());
        config.setUpgradeTimeout(nettySocketProperties.getUpgradeTimeout());
        config.setPingTimeout(nettySocketProperties.getPingTimeout());
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                String token = data.getSingleUrlParam(URL_PARAM_TOKEN);
                if (nettySocketProperties.getToken().equals(token)) {
                    return true;
                }
                return false;
            }
        });
        return new SocketIOServer(config);
    }

    /**
     * 用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

}
