# WEB模块通用组件

## **简介**（Introduction）

### **功能说明**
- 全局异常处理：`ExceptionControllerAdvice`
- `Controller` 日志打印 `ControllerLogAspect`
- `SignAuthInterceptor` 接口签名拦截器
- `@EnableWebMvc` 和 `WebMvcConfigurer` 静态文件默认配置
- `BaseController` 基类，数据绑定

### **聚合组件**
> 在正常的一个项目中，以下组件是必须要的

- Mysql数据库通用组件
- 缓存通用组件
- 多线程通用组件
- 远程通信通用组件
- 认证客户端通用组件
- swagger通用组件


## **入门篇**

### **环境准备**

    zhengcheng-parent 升级到最新版本

### **Maven 引用方式**

```
      <dependency>
            <groupId>com.zhengcheng</groupId>
            <artifactId>zc-web-spring-boot-starter</artifactId>
      </dependency>
```