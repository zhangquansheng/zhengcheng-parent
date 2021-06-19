# CHANGELOG

[typora MD编辑器](https://www.typora.io/)

## [v5.0.0] 2021.06.19

- 去掉`zc-aliyun-spring-boot-starter` 、 `zc-tencentcloud-spring-boot-starter`等大而全的通用组件，后续按照业务需要提供组件。
- 去掉`zc-cat-spring-boot-starter`，监控使用`Apache SkyWalking`[https://skywalking.apache.org/]
- （重点）提高效率：引入`MapStruct` `Orika`等`Bean`映射工具
- `BaseMessage` 增加 `timestamp`，为消息幂等使用
- 增加`zc-tk-mybatis-spring-boot-starter`，集成[MyBatis 通用 Mapper4](https://github.com/abel533/Mapper)
- 修改`zc-db-spring-boot-starter`为`zc-mybatis-plus-spring-boot-starter`，在`zc-web-spring-boot-starter`中去掉`zc-db-spring-boot-starter`等默认配置
- `redisson-spring-boot-starter` 使用示例文档

## [v4.7.0] 2020.08.01

- 修复全局Controller打印日志在异常的情况下无法打印的问题
- 以`Dubbo`暴露服务的方式使用`Feign`
- `Alibaba Nacos` 替换 `Eureka` 注册中心
- 引入 `spring cloud alibaba` 依赖
- commons-beanutils 升级到1.9.4，解决在Apache Commons Beanutils 1.9.2中，添加了一个特殊的BeanIntrospector类，该类允许抑制攻击者通过所有Java对象上可用的class属性访问类加载器的能力。但是，默认情况下，我们没有使用PropertyUtilsBean的特性。
- mybatis-plus 升级到3.3.2

## [v4.6.0] 2020.06.04
- **使用EasyCode，一个字爽**
- 去掉全局返回值的封装，为feign的继承做准备
- 增加`com.zhengcheng.common.message.BaseMessage`，消息可以继承此类，其中消息ID作用为防止重复消费
- 去掉`FeignAutoConfiguration`中关于auth的配置，后续在网关处理用户登录授权，并传递用户信息的问题
- 在`zc-web-spring-boot-starter`剔除`zc-auth-client-spring-boot-starter`，单独引用；可以网关引入后，不需要在每个服务中单独调用（推荐）。
```xml
<dependency>
    <groupId>com.zhengcheng</groupId>
    <artifactId>zc-auth-client-spring-boot-starter</artifactId>
</dependency>
``` 
- 修复`GlobalResponseBodyAdvice` 统一返回结果后，String的报错的问题
```json
{
    "code": 500,
    "message": "系统升级中，请稍后重试！",
    "data": "com.zhengcheng.common.web.Result cannot be cast to java.lang.String",
    "requestId": "6823280748b049109b0b0f40439c0265"
}
```
- 设置返回数据的类型以及编码 `produces = "application/json;charset=UTF-8"`,示例如下：
```java
    @GetMapping(value = "/str", produces = "application/json;charset=UTF-8")
    public String str(String v) {
        // 经过压测，在吞吐量为157.3/sec 的情况下，被限流的比例为36.44%，符合预期
        return "测试";
    }
```

- 去掉`zc-redis-spring-boot-starter`中`j2cache`,如果需要用，则maven添加:
```xml
    <dependency>
        <groupId>net.oschina.j2cache</groupId>
        <artifactId>j2cache-spring-boot2-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>net.oschina.j2cache</groupId>
        <artifactId>j2cache-core</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-simple</artifactId>
            </exclusion>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-api</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
```
- 增加`RedissonAutoConfiguration`配置，增加`RedissonDistributedRLock`的配置
- `zc-redis-spring-boot-starter` 更名为 `zc-cache-spring-boot-starter`



## [v4.5.0] 2020.5.22
- 增加 `GlobalResponseBodyAdvice` 统一返回结果处理
- 增加`ZkDistributedLock` zk分布式锁
- 增加`zc-zk-spring-boot-starter` Zookeeper通用组件 
- [j2cache-spring-boot2-starter](https://gitee.com/ld/J2Cache/tree/master/modules/spring-boot2-starter) 
- 增加`zc-dict-spring-boot-starter` 数据字典通用组件，[架构设计](https://note.youdao.com/ynoteshare1/index.html?id=1d514d6554d2b1519284df0a01f02bdc&type=note)
- 内存级缓存 Springboot2.x 使用 `Caffeine`
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
```xml
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
```xml
        <dependency>
            <groupId>com.ctrip.framework</groupId>
            <artifactId>cornerstone</artifactId>
        </dependency>
```
- 去掉 `eureka-client`，交给开发者自主选择(`@EnableEurekaClient`)
```xml
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
```yaml
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