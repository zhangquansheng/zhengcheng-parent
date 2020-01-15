# 缓存通用组件(redis)

## **简介**（Introduction）


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

