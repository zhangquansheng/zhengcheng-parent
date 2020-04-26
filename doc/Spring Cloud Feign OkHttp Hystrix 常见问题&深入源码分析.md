
[TOC]

# 1、Spring Cloud Feign 最佳实践
> 在项目中，调用服务接口我们推荐使用feign并开启hystix熔断。

> 由于我们未采取Springcloud微服务的方案，所以就必须要在 @FeignClient 填写 url 这个参数指定地址。

> 微服务的情况下，需要注意配置ribbon，本文章忽略之

## 1.1、推荐配置
> 开启 hystix熔断，关闭feign的重试机制，使用okhttp并关闭okhttp的重试机制，feign的回退方式使用工厂模式。

### 1) maven

```
<dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```


### 2) FeignOkHttpConfig

```
@Configuration
@ConditionalOnClass({Feign.class})
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {
 
    @Value("${feign.okhttp3.connect-timeout.milliseconds:500}")
    private Long connectTimeout;
    @Value("${feign.okhttp3.read-timeout.milliseconds:1000}")
    private Long readTimeout;
    @Value("${feign.okhttp3.write-timeout.milliseconds:60000}")
    private Long writeTimeout;
 
    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(false)  // 关闭重试机制
                .connectionPool(new okhttp3.ConnectionPool())
                .build();
    }
 
    // 关闭重试
    @Bean
    Retryer feignRetry() {
        return Retryer.NEVER_RETRY;
    }
}
```
### 3) 配置文件

```
#feign
feign:
  httpclient:
    enabled: false
  hystrix:
    enabled: true  # hystrix 启用
  okhttp:
    enabled: true
  okhttp3:
    connect-timeout:
      milliseconds: 3000
    read-timeout:
      milliseconds: 3000
    write-timeout:
      milliseconds: 60000
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000
    bokeccLive: #cc云直播的熔断超时时间为1s
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000
    study: #ep-study的熔断超时时间为1s
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000
    resource: #资源中心的熔断超时时间为3s
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000
    sso: #资源中心的熔断超时时间为3s
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 3000
  threadpool:
    default:
      coreSize: 10
      maxQueueSize: 100
    bokeccLive: # cc云直播熔断线程池配置，QPS为50
      coreSize: 50
      maxQueueSize: 100
    study:  #ep-study熔断线程池配置，QPS为10
      coreSize: 10
      maxQueueSize: 100
    resource:  #资源中心熔断线程池配置，QPS为10
      coreSize: 30
      maxQueueSize: 100
    sso:  #公共服务熔断线程池配置，QPS为10
      coreSize: 50
      maxQueueSize: 100
```

**下面就是我们线上大量系统优化后的生产经验总结：**

假设你的服务A，每秒钟会接收30个请求，同时会向服务B发起30个请求，然后每个请求的响应时长经验值大概在200ms，那么你的hystrix线程池需要多少个线程呢？

**计算公式是：30（每秒请求数量） * 0.2（每个请求的处理秒数） + 4（给点缓冲buffer） = 10（线程数量）**

必须设置合理的参数，避免高峰期，频繁的hystrix线程卡死

> 如果hystix超时时间设置为500ms，那么1s中可以处理2个线程，所以如果需要让一个服务器达到100的并发，那么核心线程数需要配置到50才能达到处理每秒100的请求；



## 1.2、推荐实现
> feign 的请求使用SpringMvc的注解，并且要求必须有回退且使用工厂模式

### 1) FeignClient

```
@FeignClient(name = "sso", url = "${services.ssoService.url}", fallbackFactory = SsoFeignClientFallbackFactory.class)
public interface SsoFeignClient {
 
    /**
     * 学生 ID 获取用户信息
     *
     * @param userId 学生 ID
     * @return 用户信息
     */
    @GetMapping(value = "/getbaseuserinfo/{userid}", headers = {"origin=gaodun.com"})
    BaseUserInfoResponse getBaseUserInfo(@PathVariable("userid") String userId);
}
```

### 2) FallbackFactory

```
@Slf4j
@Component
public class SsoFeignClientFallbackFactory implements FallbackFactory<SsoFeignClient> {
    @Override
    public SsoFeignClient create(Throwable throwable) {
        return userId -> {
            log.error("getBaseUserInfo,fallback;reason was:{}", throwable.getMessage(), throwable);
            return BaseUserInfoResponse.fallbackResult();
        };
    }
}
```

## 2、Spring Cloud Feign 性能优化


### 2.1 测试代码
feign&接口:

```
@FeignClient(name = "live", url = "http://127.0.0.1:8080/", fallbackFactory = LiveFeignClientFallbackFactory.class)
public interface LiveFeignClient {
    @GetMapping("/live/okhttp")
    BusinessResponse<Void> okhttp();
}
```

```
@GetMapping("/okhttp")
public BusinessResponse<Void> okhttp() {
    return BusinessResponse.ok();
}
```

代码的运行结果：

```
13:50:59.620 [http-nio-8080-exec-175] INFO  [] com.gaodun.storm.vod.aspect.ControllerLogAspect - LiveController.okhttp请求结束，耗时：0ms
13:50:59.619 [http-nio-8080-exec-138] INFO  [] com.gaodun.storm.vod.aspect.ControllerLogAspect - LiveController.feignOkhttp请求结束，耗时：215ms
```

**以上的代码目的是检验feign的各项配置，由于接口的响应时间几乎忽略不计，那么请求接口的响应时长的表现，就是feign在调用第三方接口时，根据并发的情况和配置情况所需要的自身处理的时长**



## 2.2 结果对比


jmeter 压测



### 1）默认项目配置测试结果
```
#使用okhttp,开启熔断
feign:
  httpclient:
    enabled: false
  hystrix:
    enabled: true 
  okhttp:
    enabled: true
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 300000
      circuitBreaker:
        requestVolumeThreshold: 10000
    live:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 300000
      circuitBreaker:
        requestVolumeThreshold: 10000
  threadpool:
    default:
      coreSize: 100
      maxQueueSize: 10000
      queueSizeRejectionThreshold: 8000
    live:
      coreSize: 100
      maxQueueSize: 10000
      queueSizeRejectionThreshold: 8000
```

默认压测结果截图：
![Image text](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/doc/image/a82b76dab8172efc5bf7e7b4cfcd6af9-%E5%8E%8B%E6%B5%8B%E7%BB%93%E6%9E%9C1.png)



### 2）根据经验配置优化以后的结果
```
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 300000
      circuitBreaker:
        requestVolumeThreshold: 10000
    live:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 1000
  threadpool:
    default:
      coreSize: 100
      maxQueueSize: 10000
      queueSizeRejectionThreshold: 8000
    live:
      coreSize: 10
      maxQueueSize: 100
```

优化后的压测结果：
![Image]()


## 2.3 总结
合理的设置核心线程池的大小，参考推荐配置；（Thread Pools 的 Active 设置的越大，当并发没有那么高的情况下，处理线程的耗时越长）

# 3、Spring Cloud Feign 实现原理
> 在微服务中，会使用到负载均衡，Feign集成了ribbon来实现，那么实际上处理 HTTP URL 请求的是 feignClient(…) 方法中的 feign.okhttp.LoadBalancerFeignClient.execute(…) 方法

其中okhttp的版本为 3.8.1，feign集成okhttp的版本如下：

```
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-okhttp</artifactId>
    <version>10.1.0</version>
</dependency>
```

## 3.1 Feign 执行流程，源码解析（非微服务的情况下）
> SynchronousMethodHandler  这里使用默认的， 如果是在微服务的情况下使用feign，那么需要分析的是：FeignLoadBalancer，也就是要理解Ribbon
### 1）SynchronousMethodHandler 同步方法处理器
请看在源码上的注释

```
final class SynchronousMethodHandler implements MethodHandler {
 
  private static final long MAX_RESPONSE_BUFFER_SIZE = 8192L;
 
  private final MethodMetadata metadata;
  private final Target<?> target;
  private final Client client;
  private final Retryer retryer;
  private final List<RequestInterceptor> requestInterceptors;
  private final Logger logger;
  private final Logger.Level logLevel;
  private final RequestTemplate.Factory buildTemplateFromArgs;
  private final Options options;
  private final Decoder decoder;
  private final ErrorDecoder errorDecoder;
  private final boolean decode404;
  private final boolean closeAfterDecode;
  private final ExceptionPropagationPolicy propagationPolicy;
 
  private SynchronousMethodHandler(Target<?> target, Client client, Retryer retryer,
      List<RequestInterceptor> requestInterceptors, Logger logger,
      Logger.Level logLevel, MethodMetadata metadata,
      RequestTemplate.Factory buildTemplateFromArgs, Options options,
      Decoder decoder, ErrorDecoder errorDecoder, boolean decode404,
      boolean closeAfterDecode, ExceptionPropagationPolicy propagationPolicy) {
    this.target = checkNotNull(target, "target");
    this.client = checkNotNull(client, "client for %s", target);
    this.retryer = checkNotNull(retryer, "retryer for %s", target);
    this.requestInterceptors =
        checkNotNull(requestInterceptors, "requestInterceptors for %s", target);
    this.logger = checkNotNull(logger, "logger for %s", target);
    this.logLevel = checkNotNull(logLevel, "logLevel for %s", target);
    this.metadata = checkNotNull(metadata, "metadata for %s", target);
    this.buildTemplateFromArgs = checkNotNull(buildTemplateFromArgs, "metadata for %s", target);
    this.options = checkNotNull(options, "options for %s", target);
    this.errorDecoder = checkNotNull(errorDecoder, "errorDecoder for %s", target);
    this.decoder = checkNotNull(decoder, "decoder for %s", target);
    this.decode404 = decode404;
    this.closeAfterDecode = closeAfterDecode;
    this.propagationPolicy = propagationPolicy;
  }
 
  @Override
  public Object invoke(Object[] argv) throws Throwable {
     
    RequestTemplate template = buildTemplateFromArgs.create(argv);
 
    Retryer retryer = this.retryer.clone();
    while (true) {
      try {
        return executeAndDecode(template);
      } catch (RetryableException e) {
        try {
          retryer.continueOrPropagate(e);
        } catch (RetryableException th) {
          Throwable cause = th.getCause();
          if (propagationPolicy == UNWRAP && cause != null) {
            throw cause;
          } else {
            throw th;
          }
        }
        if (logLevel != Logger.Level.NONE) {
          logger.logRetry(metadata.configKey(), logLevel);
        }
        continue;
      }
    }
  }
 
  Object executeAndDecode(RequestTemplate template) throws Throwable {
    Request request = targetRequest(template);
 
    if (logLevel != Logger.Level.NONE) {
      logger.logRequest(metadata.configKey(), logLevel, request);
    }
 
    Response response;
    long start = System.nanoTime();
    try {
      // 使用 OkHttpClient 获取结果
      response = client.execute(request, options);
 
    } catch (IOException e) {
      if (logLevel != Logger.Level.NONE) {
        logger.logIOException(metadata.configKey(), logLevel, e, elapsedTime(start));
      }
      throw errorExecuting(request, e);
    }
    long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
 
   // 解析此Response对象，解析后return（返回Object：可能是Response实例，也可能是decode解码后的任意类型）。
    boolean shouldClose = true;
    try {
      if (logLevel != Logger.Level.NONE) {
        response =
            logger.logAndRebufferResponse(metadata.configKey(), logLevel, response, elapsedTime);
      }
      if (Response.class == metadata.returnType()) {
        if (response.body() == null) {
          return response;
        }
        if (response.body().length() == null ||
            response.body().length() > MAX_RESPONSE_BUFFER_SIZE) {
          shouldClose = false;
          return response;
        }
        // Ensure the response body is disconnected
        byte[] bodyData = Util.toByteArray(response.body().asInputStream());
        return response.toBuilder().body(bodyData).build();
      }
      if (response.status() >= 200 && response.status() < 300) {
        if (void.class == metadata.returnType()) {
          return null;
        } else {
          Object result = decode(response);
          shouldClose = closeAfterDecode;
          return result;
        }
      } else if (decode404 && response.status() == 404 && void.class != metadata.returnType()) {
        Object result = decode(response);
        shouldClose = closeAfterDecode;
        return result;
      } else {
        throw errorDecoder.decode(metadata.configKey(), response);
      }
    } catch (IOException e) {
      if (logLevel != Logger.Level.NONE) {
        logger.logIOException(metadata.configKey(), logLevel, e, elapsedTime);
      }
      throw errorReading(request, response, e);
    } finally {
      if (shouldClose) {
        ensureClosed(response.body());
      }
    }
  }
 
  long elapsedTime(long start) {
    return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
  }
 
  Request targetRequest(RequestTemplate template) {
    for (RequestInterceptor interceptor : requestInterceptors) {
      interceptor.apply(template);
    }
    return target.apply(template);
  }
 
  Object decode(Response response) throws Throwable {
    try {
      return decoder.decode(response, metadata.returnType());
    } catch (FeignException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new DecodeException(e.getMessage(), e);
    }
  }
 
  static class Factory {
 
    private final Client client;
    private final Retryer retryer;
    private final List<RequestInterceptor> requestInterceptors;
    private final Logger logger;
    private final Logger.Level logLevel;
    private final boolean decode404;
    private final boolean closeAfterDecode;
    private final ExceptionPropagationPolicy propagationPolicy;
 
    Factory(Client client, Retryer retryer, List<RequestInterceptor> requestInterceptors,
        Logger logger, Logger.Level logLevel, boolean decode404, boolean closeAfterDecode,
        ExceptionPropagationPolicy propagationPolicy) {
      this.client = checkNotNull(client, "client");
      this.retryer = checkNotNull(retryer, "retryer");
      this.requestInterceptors = checkNotNull(requestInterceptors, "requestInterceptors");
      this.logger = checkNotNull(logger, "logger");
      this.logLevel = checkNotNull(logLevel, "logLevel");
      this.decode404 = decode404;
      this.closeAfterDecode = closeAfterDecode;
      this.propagationPolicy = propagationPolicy;
    }
 
    public MethodHandler create(Target<?> target,
                                MethodMetadata md,
                                RequestTemplate.Factory buildTemplateFromArgs,
                                Options options,
                                Decoder decoder,
                                ErrorDecoder errorDecoder) {
      return new SynchronousMethodHandler(target, client, retryer, requestInterceptors, logger,
          logLevel, md, buildTemplateFromArgs, options, decoder,
          errorDecoder, decode404, closeAfterDecode, propagationPolicy);
    }
  }
}
```


### 2）重试机制
默认的重试机制：


```
默认的请求次数为5次，如下：
参数一：为下次发起重试请求 生成间隔时间算法的参数（时间单位：毫秒）
参数二：距下次发起重试请求最大的间隔时间（时间单位：毫秒）
public Default() {
  this(100, SECONDS.toMillis(1), 5);
}
```

获取下一次重试间隔时间

```
/**
 * Calculates the time interval to a retry attempt. <br>
 * The interval increases exponentially with each attempt, at a rate of nextInterval *= 1.5
 * (where 1.5 is the backoff factor), to the maximum interval.
 *
 * @return time in nanoseconds from now until the next attempt.
 */
long nextMaxInterval() {
  long interval = (long) (period * Math.pow(1.5, attempt - 1));
  return interval > maxPeriod ? maxPeriod : interval;
}

```
取消feign重试


```
//取消重试
 @Bean
 Retryer feignRetry() {
     return Retryer.NEVER_RETRY;
 }
```

## 3.2、 Feign 使用OkHttp，Okhttp的实现原理
### 1）feign client 通过 OkHttpClient 完成request 到 Response的一次请求
使用 feign.okhttp.OkHttpClient   (注意和okhttp3.OkHttpClient是不一样的), 

实际上处理 HTTP URL 请求的是 feignClient(…) 方法中的 feign.okhttp.OkHttpClient.execute(…) 方法，源码如下：


```
@Override
public feign.Response execute(feign.Request input, feign.Request.Options options)
    throws IOException {
  okhttp3.OkHttpClient requestScoped;
  if (delegate.connectTimeoutMillis() != options.connectTimeoutMillis()
      || delegate.readTimeoutMillis() != options.readTimeoutMillis()) {
    requestScoped = delegate.newBuilder()
        .connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
        .readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS)
        .followRedirects(options.isFollowRedirects())
        .build();
  } else {
    requestScoped = delegate;
  }
  Request request = toOkHttpRequest(input);
  Response response = requestScoped.newCall(request).execute();
  return toFeignResponse(response, input).toBuilder().request(input).build();
}
```

在默认没有配置的情况下，Options的初始化参数如下：


```
public Options() {
  this(10 * 1000, 60 * 1000);
}
 
 
// connectTimeoutMillis = 10_000
// readTimeoutMillis = 60_000
```

也可以通过配置文件来修改默认配置，配置如下：


```
// default 为默认的，也可以根据feign的value值，单独配置
feign.client.config.default.connectTimeout=1000
feign.client.config.default.readTimeout=10000

```

从源码可以看到，feign使用okhttp时，超时时间优先根据client的时间来设置

### 2）okhttp 执行层

```
// 其中requestScoped 是 okhttp3.OkHttpClient 的实例
Response response = requestScoped.newCall(request).execute();

```

这是应用程序中发起网络请求最顶端的调用，newCall(request) 方法返回 RealCall 对象。

RealCall 封装了一个 request 代表一个请求调用任务，RealCall 有两个重要的方法 execute() 和 enqueue(Callback responseCallback)。

execute() 是直接在当前线程执行请求，enqueue(Callback responseCallback) 是将当前任务加到任务队列中，执行异步请求。(异步请求的执行流程会在后续讲解)

### 3)  同步请求

```
@Override public Response execute() throws IOException {
  synchronized (this) {
    if (executed) throw new IllegalStateException("Already Executed");
    executed = true;
  }
  captureCallStackTrace();
  try {
    client.dispatcher().executed(this);
    Response result = getResponseWithInterceptorChain();
    if (result == null) throw new IOException("Canceled");
    return result;
  } finally {
    client.dispatcher().finished(this);
  }
}

```

从执行层说到连接层，涉及到 getResponseWithInterceptorChain 方法中组织的各个拦截器的执行过程，其中 getResponseWithInterceptorChain 是关键，它使用了 责任链设计模式 



### 4)  连接器执行过程(关键)

```
Response getResponseWithInterceptorChain() throws IOException {
  // Build a full stack of interceptors.
  List<Interceptor> interceptors = new ArrayList<>();
  interceptors.addAll(client.interceptors());
  interceptors.add(retryAndFollowUpInterceptor);
  interceptors.add(new BridgeInterceptor(client.cookieJar()));
  interceptors.add(new CacheInterceptor(client.internalCache()));
  interceptors.add(new ConnectInterceptor(client));
  if (!forWebSocket) {
    interceptors.addAll(client.networkInterceptors());
  }
  interceptors.add(new CallServerInterceptor(forWebSocket));
 
  Interceptor.Chain chain = new RealInterceptorChain(
      interceptors, null, null, null, 0, originalRequest);
  return chain.proceed(originalRequest);
}

```

### 5)  Okhttp3 拦截器 RetryAndFollowUpInterceptor 重试机制
> 此拦截器将从故障中恢复，并根据需要执行重定向。如果调用被取消，它会抛出 *{@link IOException}

关于重定向次数：

```
/**
 * How many redirects and auth challenges should we attempt? Chrome follows 21 redirects; Firefox,
 * curl, and wget follow 20; Safari follows 16; and HTTP/1.0 recommends 5.
 */
private static final int MAX_FOLLOW_UPS = 20;

```


### 6)  Okhttp3 拦截器 BridgeInterceptor 桥梁
> 一个实现应用层和网络层直接的数据格式编码的桥。
> - 第一： 把应用层客户端传过来的请求对象转换为 Http 网络协议所需字段的请求对象。 
> - 第二:  把下游网络请求结果转换为应用层客户所需要的响应对象


默认设置HTTP长连接（开启Keep-Alive功能可使客户端到服务器端的连接持续有效,当出现对服务器的后继请求时,Keep-Alive功能避免了建立或者重新建立连接。）


```
if (userRequest.header("Connection") == null) {
  requestBuilder.header("Connection", "Keep-Alive");
}
```

### 7)  Okhttp3 拦截器  CacheInterceptor 缓存
为来自缓存的请求提供服务，并将响应写入缓存



### 8)  Okhttp3 拦截器 ConnectInterceptor 连接
打开到目标服务器的连接并继续到下一个拦截器。



### 9)  Okhttp3 拦截器 CallServerInterceptor 网络调用
这是链中的最后一个拦截器。它对服务器进行网络调用。




### 10)  ConnectionPool 实现




> 管理HTTP和HTTP/2连接的重用以减少网络延迟

> HTTP请求共享同一{@link Address} ，共享同一{@link Connection}

> 实现策略为将来使用而保持开放的连接。



默认实现中，使用一个双向队列来缓存所有连接， 这些连接中最多只能存在 5 个空闲连接，空闲连接最多只能存活 5 分钟。

```

/**
 * Create a new connection pool with tuning parameters appropriate for a single-user application.
 * The tuning parameters in this pool are subject to change in future OkHttp releases. Currently
 * this pool holds up to 5 idle connections which will be evicted after 5 minutes of inactivity.
 */
public ConnectionPool() {
  this(5, 5, TimeUnit.MINUTES);
}
```
如何复用Connection：遍历了所有的连接，然后判断某个连接是否可以复用；http1.x协议下当前socket没有其他流正在读写时可以复用，否则不行，http2.0对流数量没有限制。

如何清理连接池：每次put一个新连接的时候都会判断是否需要清理。遍历当前所有连接，跳过正在使用的连接，其他没有用的连接，如果哪个连接超过了规定的时间，就关掉这个socket。如果都没有超过规定时间的，就返回离规定时间最近的那个差值。拿到那个时间值后，我们再回到上面那个cleanupRunnable中，在那里会wait线程，然后醒来继续清理







**以上论述，仅代表个人观点，作者水平有限，如有错误，欢迎批评指正。**