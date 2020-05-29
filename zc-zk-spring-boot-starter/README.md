# ZooKeeper通用组件

## **简介**（Introduction）

> ZooKeeper is a centralized service for maintaining configuration information, naming, providing distributed synchronization, and providing group services.

> 它是一个针对大型分布式系统的可靠协调系统，提供的功能包括：配置维护、名字服务、分布式同步、组服务等

[ZooKeeper wiki](https://cwiki.apache.org/confluence/display/ZooKeeper/Index) 、[Curator](http://curator.apache.org)

### ZooKeeper典型应用场景介绍

- 数据发布与订阅（配置中心）

#### 分布式锁 ZkDistributedLock

- 分布式队列
- 负载均衡
- 命名服务......


## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本，JDK1.8

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-zk-spring-boot-starter</artifactId>
  </dependency>
```

### **设置**


### ZooKeeper 可视化工具

### ZooKeeper 在kafka中的作用

### ZooKeeper 在Dubbo(分布式服务框架)中的作用 

> 使用ZooKeeper作为注册中心


### ZooKeeper 在Elastic-Job(分布式任务)中的作用 