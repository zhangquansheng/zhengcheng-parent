package com.zhengcheng.core.socketio.runner;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.PreDestroy;

/**
 * SocketIOServer 启动
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/19 14:21
 */
@Slf4j
public class SocketIOServerCommandLineRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Autowired
    public SocketIOServerCommandLineRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("SocketIO server starting");
        server.start();
    }

    @PreDestroy
    public void stop() {
        log.info("SocketIO server stopping");
        server.stop();
    }
}
