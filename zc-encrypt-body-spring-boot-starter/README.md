# zc-encrypt-body-spring-boot-starter

`zc-encrypt-body-spring-boot-starter`是对`springboot`控制器统一的`响应体编码/加密`与`请求体解密`的注解处理方式，支持AES/RSA

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

## 使用

### 目前只支持方法级的，对单一请求生效

```java
@ApiOperation(value = "用户手机登录")
@Encrypted
@Decrypted
@PostMapping("/mobile/login")
public Result<TokenInfoDTO> mobileLogin(@RequestBody UserMobileLoginCommand userMobileLoginCommand, HttpServletRequest request) {
    SaTokenInfo saTokenInfo = oauthFacade.login(userMobileLoginCommand, request);
    return Result.successData(BeanUtil.copyProperties(saTokenInfo, TokenInfoDTO.class));
}
```

## @Encrypted

对含有`@ResponseBody`注解的控制器的响应值进行加密，默认使用`AES加密`.

### 参数值

- value
  - 释义：加密方式
  - 类型：EncryptBodyMethod
  - 默认值：EncryptBodyMethod.AES

## @Decrypted

对含有`@RequestBody`注解的控制器的请求数据进行解密，默认使用`AES加密`.

### 参数值

- value
    - 释义：解密方式
    - 类型：DecryptBodyMethod
    - 默认值：DecryptBodyMethod.AES
