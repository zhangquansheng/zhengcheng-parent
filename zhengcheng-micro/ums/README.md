# UMS 用户管理系统 

## 快速启动

### Jasypt

增加启动参数：

```
-Djasypt.encryptor.password=123456 -Dspring.profiles.active=dev
```

## 工程结构

::: tip 关于代码分层的理解
首先，**分层的目的：高内聚，低耦合**。虽然有时候一个`controller`方法里面仅仅调用一个`service`的方法，
一个`service`方法里面仅仅调用一个到`dao`层里的方法，但是这几层还是非常有必要存在的。
- 一、这样看起来结构清晰。
- 二、可扩展性和适应性更加强，比如将来用户的业务逻辑发生变化，你需要做的仅仅就是在`service`层中
  多调用一个方法即可，而不是需要对代码有太多的改动。
- 三、维护更加简单，将来维护这个代码的不一定是你本人或者开发这个系统的人，所以，如果你严格按照这种架构来写的话，
  他们只需要有这种分层意识，很容易掌握这个系统，也很容易能够对问题进行排查和修改。
  :::


> 参考阿里巴巴开发手册推荐应用分层

- common 公共的，例如配置，常量等
- controller 控制层：包括Web层，开发接口，终端显示层
    - facade 外观模式，通用处理层
        - internal 接口实现
            - assembler 传输对象组装器
- dto（DataTransferObject） 数据传输对象，Service或Facade向外传输的对象。
    - command 数据查询对象，web接收终端请求。注意超过2个参数的查询封装，禁止使用Map类来传输。
- service 业务层：包括业务逻辑层，外部接口或者第三方平台
    - impl 接口实现
    - kafka 消费队列
    - feign 外部接口或第三方平台
        - dto （DataTransferObject） 数据传输对象，Feign向外传输的对象。
        - fallback feign回退工厂
- domain 数据持久层：DAO层，数据源
    - entity DO 此对象与数据库表结构一一对应，通过DAO层向上传输数据源对象
    - enums 枚举
    - mapper (dao) mybatis-plus与数据库交互

##  测试账号

> admin/111111

在所有需要登录授权的接口中，增加 `header` 参数 `satoken`

例如在 vue-admin 中的 request.js 中增加
```
  // please modify it according to the actual situation
  config.headers['satoken'] = getToken()
```