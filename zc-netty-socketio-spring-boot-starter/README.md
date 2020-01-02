# 即时聊天通用组件

## **简介**（Introduction）

即时聊天基于[socket.io](https://github.com/socketio/socket.io)，使用[netty-socketio](https://github.com/mrniko/netty-socketio)

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本，pom文件引入

```
      <dependency>
            <groupId>com.zhengcheng</groupId>
            <artifactId>zc-netty-socketio-spring-boot-starter</artifactId>
       </dependency>
```

### **安装**


### **设置**

```
rt-server.host=localhost
rt-server.port=9092
rt-server.ping-interval=300000
rt-server.upgrade-timeout=25000
rt-server.ping-timeout=60000
rt-server.token=123456
rt-server.redisson.enable=true 
rt-server.redisson.host=127.0.0.1
rt-server.redisson.port=6379
rt-server.redisson.password=123456
```

