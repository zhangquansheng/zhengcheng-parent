# CHANGELOG

## TODO LIST
- SignAuthInterceptor  使用SDK  的sample和例子，简化对接成本
- Elasticsearch 深入研究，面试，实战
- 代码在线生成器
- SOLID 架构设计 深入学习
- cache aside pattern / redis 深入总结
- 技术分享整理
- 增加zk分布式锁，分布式锁实现方式的性能对比和使用场景整理
- 如何保证kafka消息不丢失（结合实际业务场景）

### 强烈提醒
- **需要花费很长时间的事情，需要慎重考虑一下在去做**




## [v4.2.0] 2020.4.18
- **zc-web-springboot-starter 聚合通用组件，定制化Springboot2.x 开发的最小框架支持**,[Sample项目](https://gitee.com/zhangquansheng/magic/tree/alibaba/)
- 接口签名校验，例如微信支付接口的签名校验 `SignAuthInterceptor`
- 去掉 `WebAutoConfiguration` 上 `@EnableApolloConfig`的配置，交给开发者自主选择
- 去掉 `apollo` 配置中心依赖包，交给开发者自主选择
```
        <dependency>
            <groupId>com.ctrip.framework.apollo</groupId>
            <artifactId>apollo-client</artifactId>
        </dependency>
        <dependency>
            <groupId>com.ctrip.framework.apollo</groupId>
            <artifactId>apollo-core</artifactId>
        </dependency>
```
- 去掉 `VI` 依赖包，交给开发者自主选择
```
        <dependency>
            <groupId>com.ctrip.framework</groupId>
            <artifactId>cornerstone</artifactId>
        </dependency>
```
- 去掉 `eureka-client`，交给开发者自主选择(`@EnableEurekaClient`)
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```
- 微信开发工具升级 `3.7.0` [微信开发 Java SDK（开发工具包）](https://gitee.com/binary/weixin-java-tools)
- hutool 升级到 `5.3.2`
- 增加`PageResult` 分页结果



## [v4.1.0] 2020.4.11
- `SpringCloud` 升级到 `Greenwich.SR4`
- `SpringBoot` 升级到 `2.1.11.RELEASE`
- `fastjson` 升级 `1.2.68`,`SpringBoot` 使用默认的 `jackson`，`fastjson`总是有安全的漏洞，需要升级，所以重要的地方舍弃使用。
- `SpringBoot` `jackson` 的配置如下:
```
spring:
    jackson:
       #参数意义：
       #JsonInclude.Include.ALWAYS              默认
       #JsonInclude.Include.NON_DEFAULT     属性为默认值不序列化
       #JsonInclude.Include.NON_EMPTY         属性为 空（””） 或者为 NULL 都不序列化
       #JsonInclude.Include.NON_NULL           属性为NULL   不序列化
        default-property-inclusion: ALWAYS
        time-zone: GMT+8
        date-format: yyyy-MM-dd HH:mm:ss
```
- 去掉 `MobileMask` 注解
- 取消 `feign` 重试 
- 增加分页返回结果 `PageInfo`
- 修改 `Event` 为组合 `Component` 的注解
- 新增 `RocketmqListener` 的注解，可以直接在方法上，减少代码中Class的个数 

## [v3.19.0] 2020.4.10
- 其他