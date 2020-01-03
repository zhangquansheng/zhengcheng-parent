# 阿里云消息队列通用组件

## **简介**（Introduction）

消息队列基于阿里云的[rocketmq](https://www.aliyun.com/product/rocketmq)，[源码参考](https://github.com/apache/rocketmq)

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本，JDK1.8 ，**阿里云消息队列 RocketMQ 版**

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-mq-spring-boot-starter</artifactId>
  </dependency>
```

### **设置**

```
# 您在控制台创建的 Group ID
mq.consumer.id = 
# AccessKeyId 阿里云身份验证，在阿里云服务器管理控制台创建
mq.consumer.access-key = 
# AccessKeySecret 阿里云身份验证，在阿里云服务器管理控制台创建
mq.consumer.secret-key = 
# 集群订阅方式 (默认)
mq.consumer.message-model
# 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
mq.consumer.namesrv-addr
# 主题
mq.consumer.subscriptions[0].topic = 
# 消息过滤表达式
mq.consumer.subscriptions[0].expression = 
```

```
# 您在控制台创建的 Group ID
mq.producer.id = 
# AccessKeyId 阿里云身份验证，在阿里云服务器管理控制台创建
mq.producer.access-key = 
# AccessKeySecret 阿里云身份验证，在阿里云服务器管理控制台创建
mq.producer.secret-key = 
# 发送消息超时时间
mq.producer.send-timeout = 
```

## 使用

