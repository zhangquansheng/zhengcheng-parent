# zc-encrypt-body-spring-boot-starter

`encrypt-body-spring-boot-starter`是对`springboot`控制器统一的`响应体编码/加密`与`请求体解密`的注解处理方式，支持AES/RSA

## 配置参数

```yaml
encrypt:  
    body:
      ras:
        private-key: 12345678 # RSA私钥，BASE64 加密
        public-key: 12345678 # RSA公钥，BASE64 加密
      aes:
        key: 1234567812345678 #AES秘钥
```

## 
