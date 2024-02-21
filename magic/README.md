# Magic

[Github](https://github.com/zhangquansheng/zhengcheng-parent)

项目本地启动:
> -Denv=fat -Dlogging.level.com.zhangmen=DEBUG -Deureka.client.register-with-eureka=false -Dspring.cloud.nacos.discovery.register-enabled=false

## 文档 | Documentation

[中文](http://www.zhengcheng.plus/)

### 模块

``` js
magic     
├── apollo   
├── echarts     
├── gateway                          // SpringCloud Gateway 并集成 Sa-Token
├── just-auth-RBAC                   // 集成JustAuth、Sa-Token 的RBAC模型的用户中心
├── knife4j-springfox-boot-v3        // 在Spring Boot单体架构下集成Knife4j,基于Springfox3以及OpenAPIv3
├── mybatis-plus       
├── nacos       
├── netty-socketio                   // Netty-socketio 即时通讯
├── passport                         // 集成JustAuth、Sa-Token 的SSO，统一认证中心DEMO
├── sensitive-plus                   // 数据脱密
├── webmagic                         // 爬虫 https://webmagic.io/
├── zc-web-demo                      // 基于（spring-boot-starter-web） zc-core-spring-boot-starter 的纯净版项目示例 
├──pom.xml                // [依赖] 公共依赖
```

## elasticsearch-rest-high-level-client

- 目前`spring-data-elasticsearch`底层采用`es`官方`TransportClient`，而`es`官方计划放弃`TransportClient`，工具以`es`官方推荐的`RestHighLevelClient`进行封装
- 类似于`Mybatis-Plus`一样，能够极大简化`java client API`，并不断更新，让`es`更高级的功能更轻松的使用
- 基于`elasticsearch6.4.3`版本进行开发

## SSO

[Sa-Token 文档](https://sa-token.cc/doc.html)

- 登录成功
```json
{
  "code": 200,
  "message": "请求成功",
  "data": {
    "tokenName": "satoken",
    "tokenValue": "dafcffb7-8375-489e-bdae-bcf208e8dd50",
    "isLogin": true,
    "loginId": "1",
    "loginType": "login",
    "tokenTimeout": 2592000,
    "sessionTimeout": 2592000,
    "tokenSessionTimeout": -2,
    "tokenActivityTimeout": -1,
    "loginDevice": "default-device",
    "tag": null
  }
}
```

- 登录失败
```json
{
  "code": 1,
  "message": "用户名或密码错误！",
  "data": null
}
```

- token过期/未登录
```json
{
    "code": 401,
    "message": "token已被顶下线",
    "data": null
}
```

## ngrok 微信开发地址映射工具下载及使用

> ngrok http 8080


