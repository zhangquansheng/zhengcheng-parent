package com.zhengcheng.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * NettySocket自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/18 21:31
 */
@Configuration
public class NettySocketAutoConfiguration {


    @Value("${im.host}")
    private String host;
    @Value("${im.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() throws Exception {
        com.corundumstudio.socketio.Configuration socketIoConfig = new com.corundumstudio.socketio.Configuration();
        socketIoConfig.setHostname(host);
        socketIoConfig.setPort(port);
        final SocketIOServer server = new SocketIOServer(socketIoConfig);
        return server;
    }

}
