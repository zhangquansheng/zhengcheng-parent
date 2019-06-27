# 亿级流量网站微服务架构核心技术


## 一、微服务架构

Spring Cloud 版本

- Finchley 是基于 Spring Boot 2.0.x 构建的，不支持 Spring Boot 1.5.x
- Dalston 和 Edgware 是基于 Spring Boot 1.5.x 构建的，不支持 Spring Boot 2.0.x
- Camden 构建于 Spring Boot 1.4.x，但依然能支持 Spring Boot 1.5.x

### 1.微服务注册与发现

### 2.负载均衡
#### 2.1 SLB
#### 2.2 使用Ribbon实现客户端侧负载均衡

### 3.使用Feign实现声明式REST调用

### 4.使用Hystix实现微服务的容错处理

### 5.使用Zuul构建微服务网关
#### 5.1 网关日志
#### 5.2 Zuul高可用

### 6.统一管理微服务配置
#### 6.1 推荐apollo
#### 6.2 Spring Cloud Config

### 7.实现微服务跟踪
#### 1. 推荐CAT
#### 2. 使用 Spring Cloud Sleuth 实现微服务跟踪
##### 2.1 [Spring Cloud Sleuth 与 ELK配合使用](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E5%88%86%E5%B8%83%E5%BC%8F%E8%B7%9F%E8%B8%AA%E6%97%A5%E5%BF%97%E5%A2%9E%E5%8A%A0trace.md)
##### 2.2 Spring Cloud Sleuth 与 ZipKin配合使用
#### 3. ZipKin

### 8. 日志系统
#### 8.1 ELK
#### 8.2 k8s

### 9. 自动发布系统
#### 9.1 vi
#### 9.2 tars

#### 10.系统监控
#### 10.1 Grafana

### 11.经验之道
#### 11.1 统一的技术标准

- 数据持久化框架推荐使用MyBatis，尽量不要使用JPA，nutz等其他框架,[使用Mybatis规范](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/%E4%BD%BF%E7%94%A8mybatis%E8%A7%84%E8%8C%83.md) 
- 相同的命名策略和写代码习惯，参考alibaba代码规范


#### 11.2 系统分类 

如果按照功能划分，那么大概又如下三类系统。

- 第一类是接口服务系统，这类系统提供外部接口，这些接口有读有写，写接口要考虑好写的幂等性操作，做好防刷。

- 第二类是网页类系统，用户直接从网页请求的接口，如果网页中一份数据来自多个渠道，那么需不需要合并，也是要考虑的。

- 第三类是任务类系统，比如我们常见的具有统计，数据同步等功能的系统，这类系统要考虑任务是热备还是冷备（多数都是热备），此种情况下就需要考虑好分布式任务调度的问题，包括资源分配、计算的准确性等。

#### 11.3 数据安全

- 日志脱敏
- 接口关键数据脱敏，例如手机号，邮箱等