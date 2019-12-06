# CAT

[`cat`](https://github.com/dianping/cat)  [`vi`](https://github.com/ctripcorp/vi)

## 部署点评Cat监控项目

使用maven编译CAT找不到org.unidal.maven.plugins:codegen-maven-plugin:2.3.2
> 
    1. 下载codegen-2.3.2.jar放在本地maven仓库中或者私服,需要在这里面下载https://github.com/dianping/cat/tree/mvn-repo
    2. 删除本地仓库的报错位置的 _remote.repositories 文件
    3. 执行mvn命令； mvn clean install -Dmaven.test.skip=true  -U
    
[windows 下 war 包部署开发环境](https://www.cnblogs.com/harrychinese/p/dianping-cat-server-setup.html)

## 埋点

- spring boot 
- mybatis
  > 需要使用MybatisPlus或者zc-db-spring-boot-starter
- SpringService
- feign-okhttp
  > 需要使用 feign.okhttp.OkHttpClient 或者 zc-feign-spring-boot-stater


## optional属性的作用

官方文档的说法
```
What if we dont want project D and its dependencies to be added to Project A's classpath because we know some of Project-D's dependencies (maybe Project-E for example) was missing from the repository, and you don't need/want the functionality in Project-B that depends on Project-D anyway. In this case, Project-B's developers could provide a dependency on Project-D that is <optional>true</optional>, like this:

<dependency>
  <groupId>sample.ProjectD</groupId>
  <artifactId>ProjectD</artifactId>
  <version>1.0-SNAPSHOT</version>
  <optional>true</optional>
</dependency>

```

简单的来说，就是引用不会传递。


## 常见问题

### java.lang.IllegalArgumentException: warning no match for this type name:

原因： SpringAop注解的时候,写错了类名会导致的这个问题
     
   
