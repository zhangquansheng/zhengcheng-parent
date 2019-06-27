方案一【推荐】、


1、Spring Cloud Sleuth 与 ELK 配合使用



 修改项目的logback-spring.xml

<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
 
    <define name="appId" class="com.zhangmen.arch.logger.component.common.AppIdDefiner"/>
 
    <springProperty scope="context" name="springAppName" source="spring.application.name"/>
 
    <springProperty scope="context" name="logSwitch" source="port.http.server"/>
 
    <!-- log maxFileSize -->
    <springProperty scope="context" name="MAX_FILE_SIZE" source="spring.logback.max-file-size" defaultValue="100MB"/>
 
    <!-- log maxIndex -->
    <springProperty scope="context" name="MAX_INDEX" source="spring.logback.max-index" defaultValue="5"/>
 
    <conversionRule conversionWord="hostAddress"
                    converterClass="com.zhangmen.arch.logger.component.common.HostAddressConvert"/>
 
    <!-- log base path -->
    <property name="LOG_HOME" value="/opt/logs/${appId}/applog"/>
 
    <!--STDOUT输出格式-->
    <property name="STDOUT_PATTERN"
              value='%d{yyyy-MM-dd HH:mm:ss.SSS}|%X{X-CAT-ROOT-ID:-}|%X{X-CAT-PARENT-ID:-}|%X{X-CAT-ID:-}|${springAppName:-}|[%thread]|%-5level|%logger{50} - %msg%n'/>
 
    <!--INFO输出格式-->
    <property name="INFO_PATTERN" value='{
                        "host": "%hostAddress",
                        "time": "%d{yyyy-MM-dd HH:mm:ss.SSSZ}",
                        "catRootId": "%X{X-CAT-ROOT-ID:-}",
                        "catParentId": "%X{X-CAT-PARENT-ID:-}",
                        "catId": "%X{X-CAT-ID:-}",
                        "service": "${springAppName:-}",
                        "level": "%level",
                        "class": "%logger",
                        "trace":"%X{X-B3-TraceId:-}",
                        "span":"%X{X-B3-SpanId:-}",
                        "parent":"%X{X-B3-ParentSpanId:-}",
                        "message": "%message%n%exception{10}"
                        }'/>
 
    <!--WARN输出格式-->
    <property name="WARN_PATTERN" value='{
                        "host": "%hostAddress",
                        "time": "%d{yyyy-MM-dd HH:mm:ss.SSSZ}",
                        "catRootId": "%X{X-CAT-ROOT-ID:-}",
                        "catParentId": "%X{X-CAT-PARENT-ID:-}",
                        "catId": "%X{X-CAT-ID:-}",
                        "service": "${springAppName:-}",
                        "level": "%level",
                        "class": "%logger",
                         "trace":"%X{X-B3-TraceId:-}",
                        "span":"%X{X-B3-SpanId:-}",
                        "parent":"%X{X-B3-ParentSpanId:-}",
                        "message": "%message%n%exception{10}"
                        }'/>
 
    <!--ERROR输出格式-->
    <property name="ERROR_PATTERN" value='{
                        "host": "%hostAddress",
                        "time": "%d{yyyy-MM-dd HH:mm:ss.SSSZ}",
                        "catRootId": "%X{X-CAT-ROOT-ID:-}",
                        "catParentId": "%X{X-CAT-PARENT-ID:-}",
                        "catId": "%X{X-CAT-ID:-}",
                        "service": "${springAppName:-}",
                        "level": "%level",
                        "class": "%logger",
                        "trace":"%X{X-B3-TraceId:-}",
                        "span":"%X{X-B3-SpanId:-}",
                        "parent":"%X{X-B3-ParentSpanId:-}",
                        "message": "%message%n%exception{10}"
                        }'/>
 
    <!--控制台输出格式-->
    <property name="CONSOLE_PATTERN"
      value="%d{yyyy-MM-dd HH:mm:ss.SSS}|parent:%X{X-B3-ParentSpanId:-}|span:%X{X-B3-SpanId:-}|trace:%X{X-B3-TraceId:-}|%X{X-CAT-ROOT-ID:-}|%X{X-CAT-PARENT-ID:-}|%X{X-CAT-ID:-}|${springAppName:-}|[%magenta(%thread)]| %highlight(%-5level)|%cyan(%logger{50}) - %msg%n"/>
 
    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>${CONSOLE_PATTERN}</pattern>
        </encoder>
    </appender>
 
    <include resource="logback/error-appender.xml"/>
    <include resource="logback/info-appender.xml"/>
    <include resource="logback/warn-appender.xml"/>
 
 
    <!-- 日志输出级别 -->
    <root level="INFO">
        <appender-ref ref="BASE-FILE-INFO"/>
        <appender-ref ref="BASE-FILE-WARN"/>
        <appender-ref ref="BASE-FILE-ERROR"/>
        <if condition='isNull("logSwitch")'>
            <then>
                <appender-ref ref="STDOUT"/>
            </then>
        </if>
    </root>
 
</configuration>
增加的内容如下：
"trace":"%X{X-B3-TraceId:-}",
"span":"%X{X-B3-SpanId:-}",
"parent":"%X{X-B3-ParentSpanId:-}",


2. 引入pom文件



<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>


3、日志系统中的效果





如果你两个微服务都有集成sleuth，并且使用Feign（网关如果也集成sleuth，也是可以的），那么整个trace都可以在日志系统查看到。







4、Spring Cloud Sleuth 简介

span(跨度)：基本工作单元。初始化span被称为”root span“，该 span的ID和trace的ID相等。
trace(跟踪)：一组共享”root span“的span组成的树状结果成为trace。

方案二【不推荐】、


1、 在使用了mvc-spring-boot-starter项目中直接升级到2.2即可。

<dependency>
    <groupId>com.zhangmen.boot</groupId>
    <artifactId>mvc-spring-boot-starter</artifactId>
    <version>2.2</version>
</dependency>
logback版本的日志增加traceId主要的原理和源码如下：

/**
 * 定义拦截规则：
 * 有@RequestMapping注解的方法。
 */
@Pointcut("@within(org.springframework.web.bind.annotation.RequestMapping) && !@annotation(com.zhangmen.boot.mvc.annotation.RateLimiter)")
public void controllerMethodPointcut() {
}
 
 
/**
 * 设置traceId
 */
@Before("controllerMethodPointcut()")
public void before() {
    String traceId = String.valueOf(UUID.randomUUID()).replace("-", "");
    MDC.put(TRACE_ID, traceId);
}
 
/**
 * 移除traceId
 */
@AfterReturning("controllerMethodPointcut()")
public void afterReturn() {
    MDC.remove(TRACE_ID);
}
 
/**
 * 移除traceId
 */
@AfterThrowing("controllerMethodPointcut()")
public void afterThrow() {
    MDC.remove(TRACE_ID);
}


2、 修改项目的logback-spring.xml（直接下载copy到项目中即可）,打印traceId





3、日志系统中根据traceId查看



{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "traceId": "b09968cbbd764154b8506db1c378b076"
          }
        }
      ]
    }
  }
}


4、问题

    异步多线程情况下的traceId的打印，还没有详细研究。
    其他服务对接，
    其他jar包打印的日志等问题
