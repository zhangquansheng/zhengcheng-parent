# 缓存通用组件(redis)

## **简介**（Introduction）

分布式锁，redis的布隆过滤器，CacheManager

## **入门篇**


### **环境准备**


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

    #### 性能对比



### Redis 布隆过滤器

- [RedisBloomFilter](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/zc-redis-spring-boot-starter/RedisBloomFilter.md)

### Redis的三个框架：Jedis,Redisson,Lettuce

> SpringBoot2.0默认采用Lettuce客户端来连接Redis服务端的

- Jedis：是Redis的Java实现客户端，提供了比较全面的Redis命令的支持，
- Redisson：实现了分布式和可扩展的Java数据结构。redisson官方发布了[redisson-spring-boot-starter](https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter)
- Lettuce：高级Redis客户端，用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器。

### CacheManager

> [Caching 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-caching)

