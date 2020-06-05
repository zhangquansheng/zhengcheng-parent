# 新征程框架项目

新征程云服务父项目，所有新征程微服务项目的框架支持项目，**为简化开发而生**

`简化开发` `效率至上`

## [Sample项目](https://gitee.com/zhangquansheng/magic)

## 当前稳定版本  [**更新日志**](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/CHANGELOG.md)

### 4.x
```
    <parent>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zhengcheng-parent</artifactId>
        <version>4.4.0</version>
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
zc-web-spring-boot-starter | WEB模块通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-web-spring-boot-starter)
zc-auth-client-spring-boot-starter | 认证客户端通用组件
zc-feign-spring-boot-starter | 远程通信通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/zc-feign-spring-boot-starter/README.md)
zc-db-spring-boot-starter | Mysql数据库通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-db-spring-boot-starter)
zc-cache-spring-boot-starter | 缓存通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-cache-spring-boot-starter)
zc-sentinel-spring-boot-starter | 服务降级、熔断和限流通用组件
zc-job-spring-boot-starter | XXL-JOB定时任务通用组件
zc-swagger-spring-boot-starter | swagger通用组件
zc-sharding-jdbc-spring-boot-starter | 分库分表通用组件
zc-common-spring-boot-starter | 公共库通用组件(module之间的公共部分)
zc-cat-spring-boot-starter | CAT监控通用组件[部署文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-cat-spring-boot-starter)
zc-netty-socketio-spring-boot-starter | 即时聊天通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-netty-socketio-spring-boot-starter)
zc-aliyun-spring-boot-starter | 阿里云通用组件(OSS，短信服务，RocketMQ，内容安全，日志服务，DTS)[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-aliyun-spring-boot-starter)
zc-tencentcloud-spring-boot-starter | 腾讯云通用组件（云对象存储 COS，内容安全，自然语言自动配置）[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-tencentcloud-spring-boot-starter)
zc-dict-spring-boot-starter | 数据字典通用组件（架构设计）[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-dict-spring-boot-starter)
zc-zk-spring-boot-starter | Zookeeper通用组件


### 三方包

- Java工具类库[hutool](https://hutool.cn/docs/#/)
- [MyBatis-Plus](https://mp.baomidou.com/)为简化开发而生
- [XXL-JOB](http://www.xuxueli.com/xxl-job/)分布式任务调度平台
- [Apollo配置中心](https://github.com/ctripcorp/apollo)

### 开发 

- [spring-boot](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html)
- [代码分层](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E4%BB%A3%E7%A0%81%E5%88%86%E5%B1%82%E7%9A%84%E7%90%86%E8%A7%A3.md)
- [开发手册](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C1.40.pdf)
- [自我修养](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E9%98%BF%E9%87%8C%E5%B7%A5%E7%A8%8B%E5%B8%88%E7%9A%84%E8%87%AA%E6%88%91%E4%BF%AE%E5%85%BB.pdf)
- [java 开发规范](https://note.youdao.com/ynoteshare1/index.html?id=70e5dc1447c436a9689b15f9947cdeff&type=note)
- [规范日志打印方式](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/LOG.md)

### Apollo 和 VI

* 因为使用了[携程VI](https://github.com/ctripcorp/vi)，请在启动命令中加入-Denv=pro，其中pro是代表当前的环境，一般有dev、test、uat、pro

### idea使用maven相关插件

- [mvn] -N versions:update-child-modules 根据parent的版本，更新module的版本号
- [mvn] clean source:jar javadoc:jar deploy -Dmaven.test.skip=true  跳过测试并打包发布源码和注释到远程仓库，必须要使用mvn命令，因为idea右侧maven生命周期deploy时是没有打包源码操作的，所以需要输入命令先打包源码source:jar

## **附录**

### 技术分享

- [今日头条](https://www.toutiao.com/i6796811016547074574/)

### 联系方式

- Email：952547584@qq.com
- 微信号：z088600
