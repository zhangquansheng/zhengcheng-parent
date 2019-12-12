# 新征程框架项目
新征程云服务父项目，所有新征程微服务项目的框架支持项目，**为简化开发而生**

`简化开发` `效率至上`


## 组件说明(module)

 name | description
---|---
zc-web-spring-boot-starter | [WEB模块通用组件](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-web-spring-boot-starter)
zc-auth-client-spring-boot-starter | 认证客户端通用组件
zc-feign-spring-boot-starter | 远程通信通用组件
zc-db-spring-boot-starter | 数据库通用组件
zc-mq-spring-boot-starter | 阿里云消息队列通用组件
zc-redis-spring-boot-starter | 缓存通用组件
zc-sentinel-spring-boot-starter | 服务降级、熔断和限流通用组件
zc-job-spring-boot-starter | XXL-JOB定时任务通用组件
zc-swagger-spring-boot-starter | swagger通用组件
zc-sharding-jdbc-spring-boot-starter | 分库分表通用组件
zc-common-spring-boot-starter | 公共库通用组件(module之间的公共部分)
zc-green-spring-boot-starter |  内容安全模块通用组件(阿里云)[使用文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-green-spring-boot-starter)
zc-cat-jdbc-spring-boot-starter | CAT监控通用组件[部署文档](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-cat-spring-boot-starter)


## 参考

### 常见问题

* 因为使用了[携程VI](https://github.com/ctripcorp/vi)，请在启动命令中加入-Denv=pro，其中pro是代表当前的环境，一般有dev、test、uat、pro


### SpringBoot提供的条件化注解：

条件化注解 |	配置生效条件
---|---
@ConditionalOnBean |	配置了某个特定Bean
@ConditionalOnMissingBean |	没有配置特定的Bean
@ConditionalOnClass |	Classpath里有指定的类
@ConditionalOnMissingClass |	Classpath里缺少指定的类
@ConditionalOnExpression |	给定的SpEL表达式计算结果为true
@ConditionalOnJava |	Java的版本匹配特定值或者一个范围值
@ConditionalOnJndi |	参数中给定的JNDI位置必须存在一个，如果没有参数，则需要JNDI InitialContext
@ConditionalOnProperty |	指定的配置属性要有一个明确的值
@ConditionalOnResource |	Classpath里有指定的资源
@ConditionalOnWebApplication |	这是一个Web应用程序
@ConditionalOnNotWebApplication |	这不是一个Web应用程序

### idea使用maven相关插件

- [mvn] -N versions:update-child-modules 根据parent的版本，更新module的版本号
- [mvn] clean source:jar javadoc:jar deploy -Dmaven.test.skip=true  跳过测试并打包发布源码和注释到远程仓库，必须要使用mvn命令，因为idea右侧maven生命周期deploy时是没有打包源码操作的，所以需要输入命令先打包源码source:jar