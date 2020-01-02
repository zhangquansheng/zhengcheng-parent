# WEB模块通用组件(文档补充中)

## **简介**（Introduction）

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本

### **安装**

pom文件引入

```
      <dependency>
            <groupId>com.zhengcheng</groupId>
            <artifactId>zc-web-spring-boot-starter</artifactId>
      </dependency>
```

### **设置**

## Springboot 线程池的简化及使用

## 补充

* 实际上，@Async还有一个参数，通过Bean名称来指定调用的线程池-比如上例中设置的线程池参数不满足业务需求，可以另外定义合适的线程池，调用时指明使用这个线程池-缺省时springboot会优先使用名称为'taskExecutor'的线程池，如果没有找到，才会使用其他类型为TaskExecutor或其子类的线程池。