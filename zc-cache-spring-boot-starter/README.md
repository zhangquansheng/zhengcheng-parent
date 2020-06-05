# 缓存通用组件

## **简介**（Introduction）

SpringBoot整合**Redis**、Redisson分布式锁、Caffeine、 CacheManager、开发规范、最佳实践

## **入门篇**

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-cache-boot-starter</artifactId>
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

### Redis的三个框架：Jedis,[Redisson](https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95),Lettuce

> SpringBoot2.0默认采用Lettuce客户端来连接Redis服务端的

- Redisson：实现了分布式和可扩展的Java数据结构。redisson官方发布了[redisson-spring-boot-starter](https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter)
- Lettuce：高级Redis客户端，用于线程安全同步，异步和响应使用，支持集群，Sentinel，管道和编码器。
- Jedis：是Redis的Java实现客户端，提供了比较全面的Redis命令的支持

### RedissonAutoConfiguration

#### 单机模式

```
# redisson lock
redisson.address=redis://127.0.0.1:6379
redisson.password=123456
# 默认0
redisson.database=0 
# 默认3000
redisson.timeout=3000
```

#### 哨兵模式

```
redisson.master-name=mymaster
redisson.password=123456
redisson.sentinel-addresses=127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381
# 默认0
redisson.database=0 
# 默认3000
redisson.timeout=3000
```

#### 分布式锁 

DistributedLock 的实现类有：
- RedissonDistributedLock : Redisson 分布式R锁
```
redisson.lock.enable = true
```

#### 布隆过滤器（Bloom Filter)

##### 6.8. 布隆过滤器（Bloom Filter）
Redisson利用Redis实现了Java分布式布隆过滤器（Bloom Filter）。所含最大比特数量为2^32。


```
RBloomFilter<SomeObject> bloomFilter = redisson.getBloomFilter("sample");
// 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
bloomFilter.tryInit(55000000L, 0.03);
bloomFilter.add(new SomeObject("field1Value", "field2Value"));
bloomFilter.add(new SomeObject("field5Value", "field8Value"));
bloomFilter.contains(new SomeObject("field1Value", "field8Value"));
```

###### 6.8.1. 布隆过滤器数据分片（Sharding）
基于Redis的Redisson集群分布式布隆过滤器通过RClusteredBloomFilter接口，为集群状态下的Redis环境提供了布隆过滤器数据分片的功能。 通过优化后更加有效的算法，通过压缩未使用的比特位来释放集群内存空间。每个对象的状态都将被分布在整个集群中。所含最大比特数量为2^64。在这里可以获取更多的内部信息。


```
RClusteredBloomFilter<SomeObject> bloomFilter = redisson.getClusteredBloomFilter("sample");
// 采用以下参数创建布隆过滤器
// expectedInsertions = 255000000
// falseProbability = 0.03
bloomFilter.tryInit(255000000L, 0.03);
bloomFilter.add(new SomeObject("field1Value", "field2Value"));
bloomFilter.add(new SomeObject("field5Value", "field8Value"));
bloomFilter.contains(new SomeObject("field1Value", "field8Value"));
```

该功能仅限于`Redisson PRO`版本。


参考[redisson官方文档](https://github.com/redisson/redisson/wiki/6.-%E5%88%86%E5%B8%83%E5%BC%8F%E5%AF%B9%E8%B1%A1#68-%E5%B8%83%E9%9A%86%E8%BF%87%E6%BB%A4%E5%99%A8bloom-filter)


- [ZkDistributedLock](https://gitee.com/zhangquansheng/zhengcheng-parent/tree/master/zc-zk-spring-boot-starter#%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81-zkdistributedlock) : ZooKeeper 分布式锁


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

### [开源中国 J2Cache二级缓存框架](https://gitee.com/ld/J2Cache)
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

### 最佳实践

- [阿里云 Redis 的开发规范](https://www.infoq.cn/article/K7dB5AFKI9mr5Ugbs_px)
- RequestLimit [springboot + redis + lua 实现访问量控制](https://note.youdao.com/ynoteshare1/index.html?id=7e651aa1410422934aeb92ad1ca2814c&type=note)
- Cache Aside Pattern [分布式缓存一致性解决方案(一)](https://note.youdao.com/ynoteshare1/index.html?id=9d61ce30418981ecd838a38d1d839de9&type=note)
- [value中存储过多的元素-Redis大key多key拆分方案](https://note.youdao.com/ynoteshare1/index.html?id=baed5c72e7e1752e11975523a4be2e74&type=note)
- [内存缓存（Caffeine）、二级缓存、在实际项目中的应用](https://note.youdao.com/ynoteshare1/index.html?id=1d514d6554d2b1519284df0a01f02bdc&type=note)