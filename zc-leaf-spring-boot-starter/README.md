# Leaf

- 整合[Leaf：美团分布式ID生成服务开源](https://tech.meituan.com/2019/03/07/open-source-project-leaf.html)
- [Leaf 中文文档](https://github.com/Meituan-Dianping/Leaf/blob/master/README_CN.md)
- `SnowflakeService`  号段模式：如果使用号段模式，需要建立DB表，并配置leaf.segment.jdbc.url, leaf.segment.jdbc.username, leaf.segment.jdbc.password
- `SegmentService`  `Snowflake`模式：算法取自`twitter`开源的`snowflake`算法。
```properties
# 开启leaf segment ，可以使用 SegmentService
leaf.segment.enable = true
#leaf.segment.jdbc.url=
#leaf.segment.jdbc.username=
#leaf.segment.jdbc.password=

# 开启leaf snowflake ，可以使用 SnowflakeService
leaf.snowflake.enable = true
leaf.name = com.sankuai.leaf.opensource.test
#leaf.snowflake.zk.address = 127.0.0.1:2181
# snowflake 监控当前机器的端口号，一般都是8080
leaf.snowflake.port = 8080
```
