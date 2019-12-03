# 内容安全模块通用组件(阿里云)

> [什么是内容安全](https://help.aliyun.com/document_detail/28417.html?spm=a2c4g.11174283.6.542.51647487jEe3gQ)

## 安装

> 本文基于aliyun-java-sdk-green 3.5.1 

在 Maven 工程中使用

```
    <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-green-spring-boot-starter</artifactId>
    </dependency>
```

## 初始化

在配置文件中增加：

## 文本反垃圾

> 使用文本反垃圾接口对文本内容进行色情、暴恐、涉政等风险识别。目前仅支持同步检测。一次请求可以检测多条文本，也可以检测单条文本；

**示例代码**

## 图片审核