# 新征程框架项目

新征程云服务父项目，所有`新征程`微服务项目的框架支持项目

### 4.x
```
    <parent>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zhengcheng-parent</artifactId>
        <version>4.6.0</version>
    </parent>
```
### 3.x
```
    <parent>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zhengcheng-parent</artifactId>
        <version>3.19.0</version>
    </parent>
```


### **环境准备**

- 4.x
    - `JDK 1.8 or later`
    - [Maven 3.2+](https://maven.apache.org/download.cgi)
    - [SpringBoot 2.1.11.RELEASE](https://spring.io/projects/spring-boot)
    - [SpringCloud Greenwich.SR4](https://cloud.spring.io/spring-cloud-static/Greenwich.SR4/single/spring-cloud.html)
- 3.x
    - `JDK 1.8 or later`
    - [Maven 3.2+](https://maven.apache.org/download.cgi)
    - [SpringBoot 2.0.8.RELEASE](https://spring.io/projects/spring-boot)
    - [SpringCloud Finchley.SR3](https://cloud.spring.io/spring-cloud-static/Finchley.SR4/single/spring-cloud.html)

## 组件说明(module)

 name | description
---|---
zc-web-spring-boot-starter | WEB模块通用组件
zc-auth-client-spring-boot-starter | 认证客户端通用组件
zc-feign-spring-boot-starter | 远程通信通用组件
zc-db-spring-boot-starter | Mysql数据库通用组件
zc-cache-spring-boot-starter | 缓存通用组件
zc-sentinel-spring-boot-starter | 服务降级、熔断和限流通用组件
zc-job-spring-boot-starter | XXL-JOB定时任务通用组件
zc-swagger-spring-boot-starter | swagger通用组件
zc-sharding-jdbc-spring-boot-starter | 分库分表通用组件
zc-common-spring-boot-starter | 公共库通用组件(module之间的公共部分)
zc-cat-spring-boot-starter | CAT监控通用组件
zc-netty-socketio-spring-boot-starter | 即时聊天通用组件
zc-aliyun-spring-boot-starter | 阿里云通用组件(OSS，短信服务，RocketMQ，内容安全，日志服务，DTS)
zc-tencentcloud-spring-boot-starter | 腾讯云通用组件（云对象存储 COS，内容安全，自然语言自动配置）
zc-dict-spring-boot-starter | 数据字典通用组件（架构设计）
zc-zk-spring-boot-starter | Zookeeper通用组件


### 文档 | Documentation

- [中文](http://www.zhengcheng.plus/)
- [zhengcheng.plus](http://www.zhengcheng.plus/)

### License
    
`zhengcheng` is under the Apache 2.0 license. See the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) file for details.
