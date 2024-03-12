# UMS 用户管理系统 

## 快速启动

### Jasypt

增加启动参数：

```
-Djasypt.encryptor.password=123456 -Dspring.profiles.active=dev
```

##  测试账号

> admin/111111

在所有需要登录授权的接口中，增加 `header` 参数 `satoken`

例如在 vue-admin 中的 request.js 中增加
```
  // please modify it according to the actual situation
  config.headers['satoken'] = getToken()
```

## RBAC 
