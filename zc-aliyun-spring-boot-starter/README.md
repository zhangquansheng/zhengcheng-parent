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


