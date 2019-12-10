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

## 插件

### Logback配置

如果需要使用Cat自定义的Appender，需要在logback.xml中添加如下配置：

```
    <appender name="CatAppender" class="com.dianping.cat.logback.CatLogbackAppender"></appender>

    <root level="info">
        <appender-ref ref="CatAppender" />
    </root>
```

## [AOP实现原理](https://gitee.com/zhangquansheng/interview/blob/master/frame/Proxy.md)

### 基于注解的Spring AOP开发

#### 切入点指示符

为了方法通知应用到相应过滤的目标方法上，SpringAOP提供了匹配表达式，这些表达式也叫切入点指示符

##### 通配符

在定义匹配表达式时，通配符几乎随处可见，如 *  ..  + ，它们的含义如下：

- .. ：匹配方法定义中的任意数量的参数，此外还匹配类定义中的任意数量包

```
//任意返回值，任意名称，任意参数的公共方法
execution(public * *(..))
//匹配com.zejian.dao包及其子包中所有类中的所有方法
within(com.zejian.dao..*)
```
- ＋ ：匹配给定类的任意子类

 ```
//匹配实现了DaoUser接口的所有子类的方法
within(com.zejian.dao.DaoUser+)
```

- * ：匹配任意数量的字符

 ```
//匹配com.zejian.service包及其子包中所有类的所有方法
within(com.zejian.service..*)
//匹配以set开头，参数为int类型，任意返回值的方法
execution(* set*(int))
 ```

## 常见问题

### java.lang.IllegalArgumentException: warning no match for this type name:

原因： SpringAop注解的时候,写错了类名会导致的这个问题
     
   
