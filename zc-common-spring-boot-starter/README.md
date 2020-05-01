## Springboot2.x 整合异步任务

### 初始化

```
/**
 * 线程池配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@EnableAsync
@Configuration
public class ExecutorConfig {

    @Bean("kafkaTaskExecutor")
    @ConfigurationProperties("zc.executor")
    public Executor kafkaTaskExecutor() {
        return ExecutorMdcTaskBuilder.create().build();
    }
    
}
```
其中[ExecutorMdcTaskBuilder](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/zc-common-spring-boot-starter/src/main/java/com/zhengcheng/common/async/builder/ExecutorMdcTaskBuilder.java)为since4.3.0，作用是ThreadPoolTaskExecutor建造者，打印MDC的线程池任务建造者

属性配置
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

#### @Async

```
    @Async("kafkaTaskExecutor")
    @Override
    public void asyncToutiao() {
        // 异步线程 日志打印
        log.info("异步线程 日志打印开始");
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("10s后，异步异常，日志打印结束");
    }
```