# 远程通信通用组件

## **简介**（Introduction）

基于 `Feign` `OKHttp`

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本，JDK1.8

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-feign-spring-boot-starter</artifactId>
  </dependency>
```

默认加入Feign扫码注解，注意你的FeignClient路径
```
@EnableFeignClients("com.zhengcheng.**.feign.**")
```

### **设置**

```
feign.httpclient.enabled = false
feign.okhttp.enabled = true
feign.okhttp3.read-timeout.milliseconds = 3000
feign.okhttp3.connect-timeout.milliseconds = 3000
feign.okhttp3.write-timeout.milliseconds = 60000
```

> 更多设置请参考[Feign官方文档]

### 使用requestId就可以方便服务方实现幂等

```
header 中 requestId 为uuid，在接口可以基于分布式事务实现幂等
```

### Feign OAuth2 拦截器，需要手动配置FeignClient中

```
/**
 * Feign OAuth2 拦截器，需要手动配置FeignClient中
 *
 * @author :    quansheng.zhang
 * @date :    2019/6/29 16:07
 */
public class FeignInterceptorConfig {

    /**
     * 使用feign client访问别的微服务时，将access_token放入参数或者header ，Authorization:Bearer xxx
     * 或者url?access_token=xxx
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = template -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                if (authentication instanceof OAuth2Authentication) {
                    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                    template.header("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + details.getTokenValue());
                    template.header(FeignAutoConfiguration.REQUEST_ID, IdUtil.fastSimpleUUID());
                }
            }
        };
        return requestInterceptor;
    }
}
```