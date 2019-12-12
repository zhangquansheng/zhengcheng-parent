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


CAT服务端，客户端对机器均有一定的配置要求。

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

- 匹配方法定义中的任意数量的参数，此外还匹配类定义中的任意数量包 ..

```
//任意返回值，任意名称，任意参数的公共方法
execution(public * *(..))
//匹配com.zhengcheng.dao包及其子包中所有类中的所有方法
within(com.zhengcheng.dao..*)
```
- 匹配给定类的任意子类 +

 ```
//匹配实现了UserDao接口的所有子类的方法
within(com.zhengcheng.dao.UserDao+)
```

- 匹配任意数量的字符 *

 ```
//匹配com.zhengcheng.service包及其子包中所有类的所有方法
within(com.zhengcheng.service..*)
//匹配以set开头，参数为int类型，任意返回值的方法
execution(* set*(int))
 ```

##### 类型签名表达式

为了方便类型（如接口、类名、包名）过滤方法，Spring AOP 提供了within关键字。其语法格式如下：

```
within(<type name>)
```

type name 则使用包名或者类名替换即可

```
//匹配com.zhengcheng.dao包及其子包中所有类中的所有方法
@Pointcut("within(com.zhengcheng.dao..*)")

//匹配UserDaoImpl类中所有方法
@Pointcut("within(com.zhengcheng.dao.UserDaoImpl)")

//匹配UserDaoImpl类及其子类中所有方法
@Pointcut("within(com.zhengcheng.dao.UserDaoImpl+)")

//匹配所有实现UserDao接口的类的所有方法
@Pointcut("within(com.zhengcheng.dao.UserDao+)")
```

##### 方法签名表达式

根据方法签名进行过滤，使用关键字execution，语法表达式如下

```
//scope ：方法作用域，如public,private,protect
//returnt-type：方法返回值类型
//fully-qualified-class-name：方法所在类的完全限定名称
//parameters 方法参数
execution(<scope> <return-type> <fully-qualified-class-name>.*(parameters))
```

对于给定的作用域、返回值类型、完全限定类名以及参数匹配的方法将会应用切点函数指定的通知

```
//匹配UserDaoImpl类中的所有方法
@Pointcut("execution(* com.zhengcheng.dao.UserDaoImpl.*(..))")

//匹配UserDaoImpl类中的所有公共的方法
@Pointcut("execution(public * com.zhengcheng.dao.UserDaoImpl.*(..))")

//匹配UserDaoImpl类中的所有公共方法并且返回值为int类型
@Pointcut("execution(public int com.zhengcheng.dao.UserDaoImpl.*(..))")

//匹配UserDaoImpl类中第一个参数为int类型的所有公共的方法
@Pointcut("execution(public * com.zhengcheng.dao.UserDaoImpl.*(int , ..))")
```

##### 其他指示符

- bean：Spring AOP扩展的，AspectJ没有对于指示符，用于匹配特定名称的Bean对象的执行方法；

```
//匹配名称中带有后缀Service的Bean。
@Pointcut("bean(*Service)")
private void myPointcut1(){}
```

- this ：用于匹配当前AOP代理对象类型的执行方法；请注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配

```
//匹配了任意实现了UserDao接口的代理对象的方法进行过滤
@Pointcut("this(com.zhengcheng.spring.springAop.dao.UserDao)")
private void myPointcut2(){}
```

- target ：用于匹配当前目标对象类型的执行方法；

```
//匹配了任意实现了UserDao接口的目标对象的方法进行过滤
@Pointcut("target(com.zhengcheng.spring.springAop.dao.UserDao)")
private void myPointcut3(){}
```

- @within：**用于匹配所以持有指定注解类型内的方法**；请注意与within是有区别的， within是用于匹配指定类型内的方法执行；

```
//匹配使用了MarkerAnnotation注解的类(注意是类)
@Pointcut("@within(com.zhengcheng.spring.annotation.MarkerAnnotation)")
private void myPointcut4(){}
```

- @annotation(com.zhengcheng.spring.MarkerMethodAnnotation) : 根据所应用的注解进行方法过滤

```
//匹配使用了MarkerAnnotation注解的方法(注意是方法)
@Pointcut("@annotation(com.zhengcheng.spring.annotation.MarkerAnnotation)")
private void myPointcut5(){}
```

切点指示符可以使用运算符语法进行表达式的混编，如and、or、not（或者&&、||、！）

```
//匹配了任意实现了UserDao接口的目标对象的方法并且该接口不在com.zhengcheng.dao包及其子包下
@Pointcut("target(com.zhengcheng.spring.springAop.dao.UserDao) ！within(com.zhengcheng.dao..*)")
private void myPointcut6(){}
//匹配了任意实现了UserDao接口的目标对象的方法并且该方法名称为addUser
@Pointcut("target(com.zhengcheng.spring.springAop.dao.UserDao)&&execution(* com.zhengcheng.spring.springAop.dao.UserDao.addUser(..))")
private void myPointcut7(){}
```

## 常见问题

- java.lang.IllegalArgumentException: warning no match for this type name: 原因： SpringAop注解的时候,写错了类名会导致的这个问题

- 显示“有问题的CAT服务器[ip]”,请核查一下配置文件以及客户端路由中，都修改为内网ip

- CAT的TOMCAT启动以后，重启请不要使用./shutdown.sh ，使用 ps -ef|grep tomcat 关闭(kill -9 )所有cat启动的进程
   
