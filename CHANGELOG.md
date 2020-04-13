# CHANGELOG

## TODO LIST
- `Event` 模仿 `KafkaListener` 的写法，可以直接在方法上，这样简化代码的class个数 
- 自定义注解：签名校验，例如微信支付接口的签名校验


## [v4.1.0] 2020.4.11
- `SpringCloud` 升级到 `Greenwich.SR4`
- `SpringBoot` 升级到 `2.1.11.RELEASE`
- `fastjson` 升级 `1.2.68`,`SpringBoot` 使用默认的 `jackson`，`fastjson`总是有安全的漏洞，需要升级，所以重要的地方舍弃使用。
- `SpringBoot` `jackson` 的配置如下:
```
spring:
    jackson:
       #参数意义：
       #JsonInclude.Include.ALWAYS              默认
       #JsonInclude.Include.NON_DEFAULT     属性为默认值不序列化
       #JsonInclude.Include.NON_EMPTY         属性为 空（””） 或者为 NULL 都不序列化
       #JsonInclude.Include.NON_NULL           属性为NULL   不序列化
        default-property-inclusion: ALWAYS
        time-zone: GMT+8
        date-format: yyyy-MM-dd HH:mm:ss
```
- 去掉 `MobileMask` 注解
- 取消 `feign` 重试 
- 增加分页返回结果 `PageResult`
- 修改 `Event` 为组合Component的注解

## [v3.19.0] 2020.4.10
- 其他