# 框架项目
云服务父项目，所有微服务项目的框架支持项目。

该项目提供所有可能用到的组件，并且提供这些组件自动配置的约定集成，集成该项目的子项目最好是SpringBoot项目

## 构建一个微服务
新建一个maven工程，修改pom.xml为
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zhengcheng-parent</artifactId>
        <version>1.0-RELEASE</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>test-service</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

新建一个主入口类Application.java
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```