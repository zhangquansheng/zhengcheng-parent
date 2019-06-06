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

### 4.使用Hystix实现微服务的容错处理（重要）

### 5.使用Zuul构建微服务网关
#### 5.1 网关日志
#### 5.2 Zuul高可用

### 6.统一管理微服务配置
#### 6.1 推荐apollo
#### 6.2 Spring Cloud Config

### 7.实现微服务跟踪
#### 1. 推荐CAT
#### 2. ZipKin

### 8. 日志系统
#### 8.1 ELK
#### 8.2 k8s

### 9. 自动发布系统
#### 9.1 vi
#### 9.2 tars

#### 10.系统监控
#### 10.1 Grafana