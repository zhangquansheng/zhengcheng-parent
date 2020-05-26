# 数据字典通用组件

## **简介**（Introduction）

设计思想基于本地缓存，利用Redis 的发布/订阅来实现同步

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本，JDK1.8

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-dict-spring-boot-starter</artifactId>
  </dependency>
```

### **设置**

```
spring.cache.type=caffeine
spring.cache.caffeine.spec=initialCapacity=10,maximumSize=200,expireAfterWrite=300s

zc.dict.url = http://127.0.0.1:8800/dict/items/list
zc.dict.redis.database = 12
zc.dict.redis.host = 127.0.0.1
zc.dict.redis.password = 123456
zc.dict.redis.lettuce.pool.max-active = 50
zc.dict.redis.lettuce.pool.max-idle = 20
zc.dict.redis.lettuce.pool.max-wait = 3000
zc.dict.redis.lettuce.pool.min-idle = 2
zc.dict.redis.port = 6379
zc.dict.redis.timeout = 5000
```

## 架构设计

### 背景

在公司项目中，基本都有一个公共服务：数据字典服务；一般的情况下，应用程序都是通过数据字典服务提供的接口来获取最新数据字典，这样的实现方式存在以下的几个问题：
- 通常情况下，都需要把网络认为是不可靠的，这种实现方式无法达到高可用（99.99%）的标准
- 通过接口获取最新数据字典，会导致接口高并发，那么就需要更多的服务器来支撑，浪费资源
- 通过接口访问，会存在网络传输上的消耗

基于此，我们可以考虑把数据字典做一个redis缓存，但就算是使用了redis缓存，也会存在一定程度的网络传输上的消耗，在实际应用中，数据字典是变更频率非常低的数据，可以直接缓存在应用内部，减少对redis的访问，提高响应速度。

### 一、总体设计

#### 1.1 基础模型
![Imgae Text](https://gitee.com/zhangquansheng/zhengcheng-parent/raw/master/doc/image/dict-1-1png.png)

上图是基础模型：
1. 用户在数据字典管理平台进行新增、修改字典
2. 数据字典管理平台通知数据字典客户端有字典更新
3. 数据字典从数据字典管理平台拉取最新的字典、更新本地配置


### 二、服务端设计

#### 2.1 字典发布后的实时推送设计
在字典服务管理平台中，一个重要的功能就是字典修改后实时推送到客户端。下面我们简要看一下这块是怎么设计实现的。
![Image Text](https://gitee.com/zhangquansheng/zhengcheng-parent/raw/master/doc/image/dict-send.jpg)

##### 2.1.1 发送消息的实现方式

我们使用的是[Redis Messaging (Pub/Sub)](
https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/#pubsub), 当然你还可以通过MQ，ZK等方式来实现。

### 三、客户端设计
![Imgae Text](https://gitee.com/zhangquansheng/zhengcheng-parent/raw/master/doc/image/dict-client.jpg)


#### 为什么选择Caffeine

Caffeine是基于JAVA 1.8 Version的高性能缓存库。Caffeine提供的内存缓存使用参考Google guava的API。Caffeine是基于Google Guava Cache设计经验上改进的成果. Caffeine效率明显的高于其他缓存

### 参考文档
[微服务架构~携程Apollo配置中心架构剖析](https://mp.weixin.qq.com/s/-hUaQPzfsl9Lm3IqQW3VDQ)
