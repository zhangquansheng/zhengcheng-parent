# 阿里云通用组件

## **简介**（Introduction）

`阿里云OSS`，`文本内容安全`，`消息队列 RocketMQ 版`，`阿里云短信服务`

### **安装**

在 Maven 工程中使用

```
    <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-aliyun-spring-boot-starter</artifactId>
    </dependency>
```

## 初始化

在配置文件中增加：

阿里云API的密钥配置
```
aliyun.access-key-id=您的accessKeyId
aliyun.access-key-secret=您的accessKeySecret
```

阿里云OSS属性配置
```
aliyun.oss.endpoint=
aliyun.oss.bucket-name=
aliyun.oss.domain=
```

## 文本反垃圾

> 使用文本反垃圾接口对文本内容进行色情、暴恐、涉政等风险识别。目前仅支持同步检测。一次请求可以检测多条文本，也可以检测单条文本；

**示例代码**

```
 @Autowired
 private IAliYunGreenService aliYunGreenService;
 
  // 文本内容检测
  TextSceneResult sceneResult = aliYunGreenService.antispam(IdUtil.randomUUID(), "我是一个直率，开朗的女孩，现在常住地点在，我平时的业余时间喜欢旅行，看书。  　　我目前在自营公司工作，我想时机成熟就结婚，我理想中的约会方式是牵手漫步在公园，做伴去听演唱会，共赴浪漫之旅，希望将来过依偎在沙发里看电影，相互倾听心声，共同下厨的生活。对于我的另一半，我希望他是一个幽默，责任心，成熟稳重的男士。");
  if (sceneResult.pass()) {
      // do something
  }
     
  // 批量文本内容检测   
  List<TextSceneData> textSceneDataList = new ArrayList<>();
  TextSceneData passTextSceneData = new TextSceneData();
  passTextSceneData.setDataId(IdUtil.randomUUID());
  passTextSceneData.setContent("我是一个直率，开朗的女孩，现在常住地点在，我平时的业余时间喜欢旅行，看书。  　　我目前在自营公司工作，我想时机成熟就结婚，我理想中的约会方式是牵手漫步在公园，做伴去听演唱会，共赴浪漫之旅，希望将来过依偎在沙发里看电影，相互倾听心声，共同下厨的生活。对于我的另一半，我希望他是一个幽默，责任心，成熟稳重的男士。");
  textSceneDataList.add(passTextSceneData);
  TextSceneData blockTextSceneData = new TextSceneData();
  blockTextSceneData.setDataId(IdUtil.randomUUID());
  blockTextSceneData.setContent("NB,NM,CNM，垃圾,操你妈等等");
  textSceneDataList.add(blockTextSceneData);
  List<TextSceneResult> sceneResultList = aliYunGreenService.batchAntispam(textSceneDataList);
  for (TextSceneResult sceneResult : sceneResultList) {
      if (sceneResult.pass()) {
          // do something
      }
  }   
```

返回示例

```
  {
    "code": 200,
    "content": "我是一个直率，开朗的女孩，现在常住地点在，我平时的业余时间喜欢旅行，看书。  　　我目前在自营公司工作，我想时机成熟就结婚，我理想中的约会方式是牵手漫步在公园，做伴去听演唱会，共赴浪漫之旅，希望将来过依偎在沙发里看电影，相互倾听心声，共同下厨的生活。对于我的另一半，我希望他是一个幽默，责任心，成熟稳重的男士。",
    "dataId": "e77c08b3-1063-4c5f-9350-469a25deecb8",
    "msg": "OK",
    "results": [
      {
        "label": "normal",
        "rate": 99.91,
        "scene": "antispam",
        "suggestion": "pass"
      }
    ],
    "taskId": "txt4Ls98drerDy6g$MbjZoETU-1rIL@D"
  }
```


## 图片审核

### [同步检测](https://help.aliyun.com/document_detail/70292.html?spm=a2c4g.11186623.6.618.6b031e7fBt51sn)

**示例代码**
```
    List<ImageSceneData> imageSceneDataList = new ArrayList<>();
    ImageSceneData imageSceneData = new ImageSceneData();
    imageSceneData.setUrl("https://queqiaohui.oss-cn-hangzhou.aliyuncs.com/20191122114705-df1ed5d7-b596-4394-8f50-389693d3bd05.jpg");
    imageSceneData.setDataId(IdUtil.randomUUID());
    imageSceneDataList.add(imageSceneData);
    List<ImageSceneResult> imageSceneResultList = aliYunGreenService.batchImageSyncScan(Arrays.asList("porn"), imageSceneDataList);
    for (ImageSceneResult sceneResult : imageSceneResultList) {
        if (sceneResult.pass()) {
            // do something
        }
    }
```

返回示例
```
    {
      "code": 200,
      "dataId": "1794b28b-1d0d-464c-acc7-475aa2f7ee60",
      "extras": {},
      "msg": "OK",
      "results": [
        {
          "label": "normal",
          "rate": 99.09,
          "scene": "porn",
          "suggestion": "pass"
        }
      ],
      "taskId": "img5KZKigb4bGs7lBh3ny$tBA-1rIMvR",
      "url": "https://queqiaohui.oss-cn-hangzhou.aliyuncs.com/20191122114705-df1ed5d7-b596-4394-8f50-389693d3bd05.jpg"
    }
```

## 消息队列

### **设置**

```
# 您在控制台创建的 Group ID
aliyun.mq.consumer.id = 
# 集群订阅方式 (默认)
aliyun.mq.consumer.message-model =
# 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
aliyun.mq.consumer.namesrv-addr = 
# 主题
aliyun.mq.consumer.subscriptions[0].topic = 
# 消息过滤表达式
aliyun.mq.consumer.subscriptions[0].expression = 
```

```
# 您在控制台创建的 Group ID
aliyun.mq.producer.id = 
# 发送消息超时时间
aliyun.mq.producer.send-timeout = 
```

### 使用

#### 消费者

```
@Component
// TAG模式过滤
@Event({"EVENT_0001"})
public class DemoHandler implements IConsumerHandler{

     @Override
     public Action execute(String body){
        // TODO body是收到的消息，完成消费动作
        return Action.CommitMessage;
     }
}
```

## Logback 日志投递到阿里云日志服务

> 配置文件  [logback-aliyun.xml](https://gitee.com/zhangquansheng/zhengcheng-parent/blob/master/zc-aliyun-spring-boot-starter/logback-aliyun.xml)

在 Maven 工程中使用

```
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
    </dependency>
    <dependency>
        <groupId>com.aliyun.openservices</groupId>
        <artifactId>aliyun-log-logback-appender</artifactId>
    </dependency>
```

### 配置参数

| 参数 | 类型 | 默认值 | 说明 |
| :--- | :---: | :---: | --- |
| aliyun.access-key-id | String | 无 | 您的accessKeyId，**必填** |
| aliyun.access-key-secret | String | 无 | 您的accessKeySecret，**必填** |
| aliyun.log.endpoint | String | 无 | 日志服务的 HTTP 地址 **必填**|
| aliyun.log.project | String | kk-log-proj | 日志服务的 project 名 **必填**|
| aliyun.log.log-store | String | apps-shanghai | 日志服务的 logStore 名 **必填**|
| aliyun.log.topic | String | 无 | 日志主题，**必填** |
| aliyun.log.total-size-in-bytes | int | 100 * 1024 * 1024 | 单个producer实例能缓存的日志大小上限，默认为100MB。 |
| aliyun.log.max-block-ms | long | 60*1000 | 如果producer可用空间不足，调用者在send方法上的最大阻塞时间，默认为60秒。如果超过这个时间后所需空间仍无法得到满足，send方法会抛出TimeoutException。如果将该值设为0，当所需空间无法得到满足时，send方法会立即抛出TimeoutException。如果您希望send方法一直阻塞直到所需空间得到满足，可将该值设为负数。 |
| aliyun.log.io-thread-count | int | availableProcessors | 执行日志发送任务的线程池大小，默认为可用处理器个数。 |
| aliyun.log.batch-size-threshold-in-bytes | int | 512 * 1024 | 当一个ProducerBatch中缓存的日志大小大于等于batchSizeThresholdInBytes时，该batch将被发送，默认为512KB，最大可设置成5MB。 |
| aliyun.log.batch-count-threshold | int | 4096 | 当一个ProducerBatch中缓存的日志条数大于等于batchCountThreshold时，该batch将被发送，默认为4096，最大可设置成40960。 |
| aliyun.log.linger-ms | int | 2000 | 一个ProducerBatch从创建到可发送的逗留时间，默认为2秒，最小可设置成100毫秒。 |
| aliyun.log.retries | int | 10 | 如果某个ProducerBatch首次发送失败，能够对其重试的次数，默认为10次。如果retries小于等于0，该ProducerBatch首次发送失败后将直接进入失败队列。 |
| aliyun.log.max-reserved-attempts | int | 11 | 每个ProducerBatch每次被尝试发送都对应着一个Attempt，此参数用来控制返回给用户的attempt个数，默认只保留最近的11次attempt信息。该参数越大能让您追溯更多的信息，但同时也会消耗更多的内存。 |
| aliyun.log.base-retry-backoff-ms | long | 100 | 默认值 | 首次重试的退避时间，默认为100毫秒。Producer采样指数退避算法，第N次重试的计划等待时间为baseRetryBackoffMs*2^(N-1)。 |
| aliyun.log.max-retry-backoff-ms | long | 50 * 1000 | 重试的最大退避时间，默认为50秒。 |

参阅：[Aliyun LOG Java Producer](https://github.com/aliyun/aliyun-log-java-producer)


