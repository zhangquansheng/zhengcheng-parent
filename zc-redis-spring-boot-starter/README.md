# 缓存通用组件(redis)

## **简介**（Introduction）

SpringBoot整合Redis、分布式锁、 CacheManager

## **入门篇**

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-redis-boot-starter</artifactId>
  </dependency>
```

### **设置**

```
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    # 密码 没有则可以不填
    password: 123456
    # 如果使用的jedis 则将lettuce改成jedis即可
    lettuce:
      pool:
        # 最大活跃链接数 默认8
        max-active: 8
        # 最大空闲连接数 默认8
        max-idle: 8
        # 最小空闲连接数 默认0
        min-idle: 0
```

### 分布式锁 

AbstractDistributedLock 的实现类有：
- RedissonDistributedLock : Redisson 分布式锁
- RedisDistributedLock : Redis 分布式锁
- CuratorDistributedLock : Curator实现zk分布式锁


### Redis的三个框架：Jedis,Redisson,Lettuce

> SpringBoot2.0默认采用Lettuce客户端来连接Redis服务端的

- Jedis：是Redis的Java实现客户端，提供了比较全面的Redis命令的支持，
- Redisson：实现了分布式和可扩展的Java数据结构。redisson官方发布了[redisson-spring-boot-starter](https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter)
- Lettuce：高级Redis客户端，用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器。

### CacheManager

> [Caching 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-caching)

推荐使用 `caffeine` 性能最优
> Caffeine是基于JAVA 1.8 Version的高性能缓存库。Caffeine提供的内存缓存使用参考Google guava的API。Caffeine是基于Google Guava Cache设计经验上改进的成果.
> **Caffeine效率明显的高于其他缓存**


使用业务场景：
> 就算是使用了redis缓存，也会存在一定程度的网络传输上的消耗，在实际应用当中，会存在一些变更频率非常低的数据，
> 就可以直接缓存在应用内部，对于一些实时性要求不太高的数据，也可以在应用内部缓存一定时间，减少对redis的访问，提高响应速度。


示例配置：  
```
spring.cache.type=caffeine
spring.cache.caffeine.spec=initialCapacity=10,maximumSize=200,expireAfterWrite=3s
```

### RequestLimit 
> springboot + redis + lua 实现访问量控制

### J2Cache
```
   <dependency>
        <groupId>net.oschina.j2cache</groupId>
        <artifactId>j2cache-core</artifactId>
        <exclusions>
            <exclusion>
                <artifactId>slf4j-api</artifactId>
                <groupId>org.slf4j</groupId>
            </exclusion>
            <exclusion>
                <artifactId>slf4j-simple</artifactId>
                <groupId>org.slf4j</groupId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>net.oschina.j2cache</groupId>
        <artifactId>j2cache-spring-boot2-starter</artifactId>
    </dependency>
```
