## 一、代码编写
###（一）方法
- 1.【强制】方法出入参不准使用Map
- 2.【强制】方法返回值不准为NULL，除非真的有必要，对象用 Optional 修饰，集合返回空，而不是NULL
- 3.【推荐】if 判断最多不能超过2层，如果if的判断条件复杂，抽出来单独方法处理
- 4.【推荐】方法尽可能的简单，每个方法最多不超过80行
- 5.【推荐】注意类访问修饰符以及static 关键字的使用。避免暴露不需要的状态或者行为。
- 6.【推荐】使用通用工具函数
- 7.【强制】Integer Long 等大小比较使用 compareTo，等于比较使用 Objects.equals
- 8.【推荐】变量即取即用
- 9.【推荐】对于可预见容量大小的集合类，在初始化时应该设置capacity
- 10.【推荐】 将常量声明为static final，并以大写命名
- 11.【推荐】不要创建一些不使用的对象，不要导入一些不使用的类
（二）注释
1.【强制】service，facade的接口方法必须有注释
2.【推荐】删除无用的代码而不是注释掉，git会记录一切的；提交代码之前必须要格式化
（三）日志处理
1.【强制】日志打印统一使用slf4j，禁止使用log4j的方法
2.【强制】打印异常日志
3.【推荐】打印INFO日志，参数使用占位符，不要手动拼接，且参数用[]包含
（四）并发处理
1.【强制】线程池不允许使用 Executors 去创建，在Springboot2.x中，建议使用通过一下方式，并且建议，不同的业务之间使用不同的线程池配置
2.【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
3.【强制】 SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为 static，必须加锁，或者使用 DateUtils 工具类。
4.【强制】尽最大可能不要使用 for update 这种悲观锁
（五）远程调用
1.【强制】网络RPC请求，不准使用restTemplate等其他的形式，请使用Feign
2.【强制】 feign 的请求注解不准使用 @RequestLine
（六）ORM映射
1.【强制】在表查询中，一律不要使用 * 作为查询的字段列表，需要哪些字段必须明确写明。
2.【强制】不允许直接拿 HashMap 与 Hashtable 作为查询结果集的输出。
3.【推荐】禁止在系统中写超过3个表以上的关联查询，多表的情况下你可以分配查询相关数据，然后在程序中完成查询逻辑
4.【强制】查询数据列表必须使用LIMIT
5.【推荐】PageHelper禁止使用它自带的total总数查询，必须手写SQL查询总数。
6.【推荐】MyBatis查询函数返回列表和数据项不为空，可以不用空指针判断
二、IDEA 开发
（一）常用的插件
（二）消除 IDEA 的警告
三、发布流程要求
一、代码编写

 PDF

（一）方法
1.【强制】方法出入参不准使用Map
不够透明，开发人员比较容易混乱
失去了严谨的代码规范，出现bug难找
2.【强制】方法返回值不准为NULL，除非真的有必要，对象用 Optional 修饰，集合返回空，而不是NULL
说明：返回 null ，需要调用方强制检测 null ，否则就会抛出空指针异常。返回空数组或空集合，有效地避免了调用方因为未检测 null 而抛出空指针异常，还可以删除调用方检测 null 的语句使代码更简洁。

Optional 最佳实践:



避免使用Optional.isPresent()来检查实例是否存在（上面的举例中提到过），因为这种方式和null != obj没有区别，这样用就没什么意义了。

避免使用Optional.get()方式来获取实例对象，因为使用前需要使用Optional.isPresent()来检查实例是否存在，否则会出现NoSuchElementException异常问题。所以使用orElse()，orElseGet()，orElseThrow()获得你的结果

避免使用Optional作为类或者实例的属性，而应该在返回值中用来包装返回实例对象。

避免使用Optional作为方法的参数

不要将null赋给Optional

只有每当结果不确定时，使用Optional作为返回类型，从某种意义上讲，这是使用Optional的唯一好地方，用java官方的话讲就是：我们的目的是为库方法的返回类型提供一种有限的机制，其中需要一种明确的方式来表示“无结果”，并且对于这样的方法使用null 绝对可能导致错误

不要为了链方法而使用optional

使所有表达式成为单行lambda，如果你不会写，请在写完你的逻辑以后，看看右边IDEA的提示，会自动帮你转成lambda的

3.【推荐】if 判断最多不能超过2层，如果if的判断条件复杂，抽出来单独方法处理


4.【推荐】方法尽可能的简单，每个方法最多不超过80行


尽量让每个功能方法都简单，方便自己和他人维护你的代码

5.【推荐】注意类访问修饰符以及static 关键字的使用。避免暴露不需要的状态或者行为。


6.【推荐】使用通用工具函数
说明：函数式编程，业务代码减少，逻辑一目了然；通用工具函数hutool ，逻辑考虑周全，出问题概率低。

反例：thisName != null && thisName.equals(name); 

正例：Objects.equals(name, thisName);

7.【强制】Intege Long 等大小比较使用 compareTo，等于比较使用 Objects.equals
说明：

1、数值类型，值在-128 ~ 127的之间的数值对象，在Integer或者Long....的内部类中IntegerCache中。没有实质性创建对象或者说对象都内部类的cache[]数组中，使用==没有问题返回true，因为是同一对象。

2、数值在-128 ~ 127范围之外的数值类型，都重新创建了对象。再使用“==”，就返回false了。

3、对象的 equals 方法容易抛空指针异常，应使用常量或确定有值的对象来调用 equals 方法。当然，使用 java.util.Objects.equals() 方法是最佳实践。

8.【推荐】变量即取即用


9.【推荐】对于可预见容量大小的集合类，在初始化时应该设置capacity
说明：java 的集合类用起来十分方便，但是看源码可知，集合也是有大小限制的。每次扩容的时间复杂度很有可能是 O(n) ，所以尽量指定可预知的集合大小，能减少集合的扩容次数



10.【推荐】 将常量声明为static final，并以大写命名
说明：这样在编译期间就可以把这些内容放入常量池中，避免运行期间计算生成常量的值。另外，将常量的名字以大写命名也可以方便区分出常量与变量。



11.【推荐】不要创建一些不使用的对象，不要导入一些不使用的类
说明：这毫无意义，如果代码中出现”The value of the local variable i is not used”、”The import java.util is never used”，那么请删除这些无用的内容



（二）注释
1.【强制】service，facade的接口方法必须有注释
普通接口、属性注释要全面，让调用者可以不去问你就知道该怎么使用该接口，而不是每个人都要问一遍，无形之中增加沟通成本

2.【推荐】删除无用的代码而不是注释掉，git会记录一切的；提交代码之前必须要格式化
保持代码整洁



（三）日志处理
1.【强制】日志打印统一使用slf4j，禁止使用log4j的方法
主要是更换日志框架的时候更为方便，可以使用  lombok.extern.slf4j.Slf4j 注解

2.【强制】打印异常日志
//  不可以使用,因为这样只能在控制台查看到异常，无法记录到日志服务平台（阿里云日志服务）
e.printStackTrace();
// 参数中第一个是异常的信息，第二个是异常，这个是必须的，便于快速查看到异常的位置
log.error("createRoom,fallback;reason was:{}", e.getMessage(), e);
3.【推荐】打印INFO日志，参数使用占位符，不要手动拼接，且参数用[]包含
log.info("CC直播回调接口,直播开始回调,userId[{}],roomId[{}],liveId[{}],type[{}],startTime[{}]", userId, roomId, liveId, type, startTime);


（四）并发处理
1.【强制】线程池不允许使用 Executors 去创建，在Springboot2.x中，建议使用通过一下方式，并且建议，不同的业务之间使用不同的线程池配置


// 启用
@EnableAsync
 
 
// 配置
@Bean("kafkaTaskExecutor")
public Executor kafkaTaskExecutor() {
    ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
    executor.setCorePoolSize(50);
    executor.setMaxPoolSize(500);
    executor.setQueueCapacity(2000);
    executor.setKeepAliveSeconds(10);
    executor.setThreadNamePrefix("kafka-task-executor");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    //执行初始化
    executor.initialize();
    return executor;
}
 
 
// 使用
@Async("bokeccLiveUserActionTaskExecutor")

2.【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
说明：使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源的开销，解决 资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或 者“过度切换”的问题。

3.【强制】 SimpleDateFormat 是线程不安全的类，一般不要定义为 static 变量，如果定义为 static，必须加锁，或者使用 DateUtils 工具类。
说明：工具类可以使用：hutool ，不要自己在百度copy了

4.【强制】尽最大可能不要使用 for update 这种悲观锁
说明：容易造成死锁；使用乐观锁方式也能实现同样的效果：SQL示例：  update tbl_user set name = 'update',version = 3 where id = 100 and version = 2

（五）远程调用
1.【强制】网络RPC请求，不准使用restTemplate等其他的形式，请使用Feign
说明： 一般我们的服务中feign有线程池，也集成了hystix，有服务降级，熔断功能

2.【强制】 feign 的请求注解不准使用 @RequestLine
说明：使用SpringMvc的注解，保持统一



（六）ORM映射
1.【强制】在表查询中，一律不要使用 * 作为查询的字段列表，需要哪些字段必须明确写明。
说明：1）增加查询分析器解析成本。2）增减字段容易与 resultMap 配置不一致。3）无用字 段增加网络消耗，尤其是 text 类型的字段。



2.【强制】不允许直接拿 HashMap 与 Hashtable 作为查询结果集的输出。


3.【推荐】禁止在系统中写超过3个表以上的关联查询，多表的情况下你可以分配查询相关数据，然后在程序中完成查询逻辑
说明：降低SQL的复杂度，数据库是最难优化性能的地方

4.【强制】查询数据列表必须使用LIMIT
说明：防止误操作过多数据到内存中，可以使用PageHelper.startPage(param.getPageNo(), param.getPageSize(), false).doSelectPageInfo来控制





5.【推荐】PageHelper禁止使用它自带的total总数查询，必须手写SQL查询总数。
说明：分页插件自动的total不是最优化的SQL



6.【推荐】MyBatis查询函数返回列表和数据项不为空，可以不用空指针判断
说明：MyBatis是一款优秀的持久层框架，是在项目中使用的最广泛的数据库中间件之一。通过对MyBatis源码进行分析，查询函数返回的列表和数据项都不为空，在代码中可以不用进行空指针判断。

这样做的主要受益：

避免不必要的空指针判断，精简业务代码处理逻辑，提高业务代码运行效率；

这些不必要的空指针判断，基本属于永远不执行的Death代码，删除有助于代码维护。



二、IDEA 开发


（一）常用的插件








（二）消除 IDEA 的警告


mybatis 中，由于检测不到mapper这个bean, 所以可以忽略这个警告
三、发布流程要求