# Mysql数据库通用组件

## **简介**（Introduction）

数据库基于Mysql5.6以上，使用[MybatisPlus](https://mp.baomidou.com/)

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到3.10.0以上，pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-db-spring-boot-starter</artifactId>
  </dependency>
```

### **安装**

在启动类上加入Mapper扫码注解，注意修改成你的mapper路径
```
@MapperScan(basePackages = "com.zhengcheng.user.mapper*")
```

### **设置**

mybatis-plus.mapper-locations = classpath*:**/*Mapper.xml
mybatis-plus.type-aliases-package = com.zhengcheng.user.entity
mybatis-plus.configuration.map-underscore-to-camel-case = true
mybatis-plus.type-enums-package = com.zhengcheng.user.enums

更多设置请参考MybatisPlus官方文档
