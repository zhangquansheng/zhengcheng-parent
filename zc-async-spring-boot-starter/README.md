# 多线程通用组件

## Springboot2.x整合异步任务

### **安装**

在 Maven 工程中使用

```
    <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-async-spring-boot-starter</artifactId>
    </dependency>
```

### 初始化

**属性配置（可无，使用默认值）**

```
# 核心线程数：线程池创建时候初始化的线程数
zc.executor.core-pool-size=10
# 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
zc.executor.max-pool-size=20
#  缓冲队列：用来缓冲执行任务的队列
zc.executor.queue-capacity=2000
# 允许线程的空闲时间(秒)：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
zc.executor.keep-alive-seconds=60
# 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
zc.executor.await-termination-seconds=10
# 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
zc.executor.thread-name-prefix=default-executor-
```
> 线程拒绝策略：不在新线程中执行任务，而是有调用者所在的线程来执行 ThreadPoolExecutor.CallerRunsPolicy

### 使用

#### 添加装饰器，为mdc traceId
```
  executor.setTaskDecorator(new MdcTaskDecorator());
```

#### @Async

> @Async还有一个参数，通过Bean名称来指定调用的线程池-比如上例中设置的线程池参数不满足业务需求，可以另外定义合适的线程池，调用时指明使用这个线程池-缺省时springboot会优先使用名称为'taskExecutor'的线程池，如果没有找到，才会使用其他类型为TaskExecutor或其子类的线程池。
