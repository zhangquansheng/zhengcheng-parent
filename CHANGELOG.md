# CHANGELOG

## TODO LIST

- **kafka 在Springboot2.x 的最佳实战**
- **redis 在Springboot2.x 的最佳实战**
- [Java 两级缓存框架](https://gitee.com/ld/J2Cache) 的深入研究
- 建造者设计模式的熟悉（常见的设计模式总结）
- Elasticsearch 深入研究，面试，实战
- [代码在线生成器](https://gitee.com/zhangquansheng/magic/tree/springboot-code-gen/)
- SOLID 架构设计 深入学习
- cache aside pattern / redis 深入总结
- 技术分享整理
- 增加zk分布式锁，分布式锁实现方式的性能对比和使用场景整理 ZookeeperLock
- 如何保证kafka消息不丢失（结合实际业务场景）

### 强烈提醒
- **需要花费很长时间的事情，需要慎重考虑一下在去做**
- **对未来乐观**
- **延迟满足，坚持**

## [v4.5.0] 2020.5.22
- 增加[J2Cache —— 基于内存和 Redis 的两级 Java 缓存框架](https://gitee.com/ld/J2Cache)
- 修改`RedisTemplate<String, Object>` value的序列化方式（不兼容老版本，有可能出现value反序列化异常）

## [v4.4.0] 2020.5.7

- 增加`RequestLimit`注解，实现接口访问量控制
- `CuratorDistributedLock`  `RedisBloomFilter` `BloomFilterHelper` 即将作废
- `SignAuthInterceptor` 简化对接成本;
    - [SignAuthFeignConfig](https://gitee.com/zhangquansheng/magic/blob/springboot-code-gen/src/main/java/com/zhengcheng/magic/common/config/SignAuthFeignConfig.java)
- 增加 `SignAuthUtils` 工具类
- `zc-feign-spring-boot-starter` 远程通信通用组件，默认配置中增加SignAuth的参数，详细见`com.zhengcheng.feign.FeignAutoConfiguration`,其中默认的秘钥为：security.api.key，请在配置文件中写入秘钥，默认为 `zhengcheng` 
- 技术分享 [API接口防止参数篡改和重放攻击](https://note.youdao.com/ynoteshare1/index.html?id=ed15f29e7ad1ff2d15a1236231283bc7&type=note)
- 删除 `FeignInterceptorConfig` , 默认配置中增加 Feign OAuth2 拦截器
- `TraceIdInterceptor` 增加 `applicationName` 构造参数，修复无法打印项目名的问题
- [最终一致性的实现方案](http://note.youdao.com/noteshare?id=53594daefb9d2eff4cd9c353d5963f92&sub=DD547814891346F0BEF0115B425D47C2)


## [v4.3.0] 2020.4.18

- 新增`ExecutorMdcTaskBuilder` ({@link ThreadPoolTaskExecutor} 建造者) 打印MDC的线程池任务建造者
- 删除zc-async-spring-boot-starter,异步线程池的配置简化并交给开发者
- Feign日志打印成INFO，**方便线上问题定位**
- 增加MDC链路traceId，返回值也增加request_id，方便线上问题定位，参考腾讯云
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
- 增加 `feign` 重试，只会对GET请求重试，提高系统的可用性


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