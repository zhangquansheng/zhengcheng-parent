# 新征程框架项目
新征程云服务父项目，所有新征程微服务项目的框架支持项目，**为简化开发而生**

`简化开发` `效率至上`


## 组件说明(module)

 name | description
---|---
zc-web-spring-boot-starter | WEB模块通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-web-spring-boot-starter)
zc-auth-client-spring-boot-starter | 认证客户端通用组件
zc-feign-spring-boot-starter | 远程通信通用组件
zc-db-spring-boot-starter | Mysql数据库通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-db-spring-boot-starter)
zc-mq-spring-boot-starter | 阿里云消息队列通用组件
zc-redis-spring-boot-starter | 缓存通用组件
zc-sentinel-spring-boot-starter | 服务降级、熔断和限流通用组件
zc-job-spring-boot-starter | XXL-JOB定时任务通用组件
zc-swagger-spring-boot-starter | swagger通用组件
zc-sharding-jdbc-spring-boot-starter | 分库分表通用组件
zc-common-spring-boot-starter | 公共库通用组件(module之间的公共部分)
zc-green-spring-boot-starter |  内容安全模块通用组件(阿里云)[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-green-spring-boot-starter)
zc-cat-jdbc-spring-boot-starter | CAT监控通用组件[部署文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-cat-spring-boot-starter)
zc-netty-socketio-spring-boot-starter | 即时聊天通用组件[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-netty-socketio-spring-boot-starter)
## **FAQ**

### 开发 

- [代码分层](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E4%BB%A3%E7%A0%81%E5%88%86%E5%B1%82%E7%9A%84%E7%90%86%E8%A7%A3.md)
- [开发手册](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C1.40.pdf)
- [自我修养](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E9%98%BF%E9%87%8C%E5%B7%A5%E7%A8%8B%E5%B8%88%E7%9A%84%E8%87%AA%E6%88%91%E4%BF%AE%E5%85%BB.pdf)
 
### Apollo 和 VI

* 因为使用了[携程VI](https://github.com/ctripcorp/vi)，请在启动命令中加入-Denv=pro，其中pro是代表当前的环境，一般有dev、test、uat、pro

### idea使用maven相关插件

- [mvn] -N versions:update-child-modules 根据parent的版本，更新module的版本号
- [mvn] clean source:jar javadoc:jar deploy -Dmaven.test.skip=true  跳过测试并打包发布源码和注释到远程仓库，必须要使用mvn命令，因为idea右侧maven生命周期deploy时是没有打包源码操作的，所以需要输入命令先打包源码source:jar

## **附录**

### 联系方式

- Email：952547584@qq.com
- 微信号：z088600
