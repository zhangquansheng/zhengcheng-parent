# 布隆过滤器 Springboot2.x redis 

## 背景

在开发`备胎小计划`直播项目中，存在这样一种情况：创建10万人大班课直播间，需要对进入直播间的学生鉴权，**为了防止其他没有权限的学生对进入直播间时对数据库产生无效的查询，这里使用布隆过滤器，过滤掉一定没有权限的学生，由于我们系统是分布式的，所里这里选型使用Redis--位图BitMap来实现**

## 布隆过滤器概述

布隆过滤器（英语：Bloom Filter）是1970年由布隆提出的。它实际上是一个很长的二进制向量和一系列随机映射函数。布隆过滤器可以用于检索一个元素是否在一个集合中。它的优点是空间效率和查询时间都远远超过一般的算法，缺点是有一定的误识别率和删除困难。

布隆过滤器的原理是，当一个元素被加入集合时，通过K个散列函数将这个元素映射成一个位数组中的K个点，把它们置为1。检索时，我们只要看看这些点是不是都是1就（大约）知道集合中有没有它了：如果这些点有任何一个0，则被检元素一定不在；如果都是1，则被检元素很可能在。这就是布隆过滤器的基本思想。

参考：
- https://www.cnblogs.com/z941030/p/9218356.html：
- https://zhuanlan.zhihu.com/p/94433082

## 开发环境

- `JDK 1.8 or later`
- `Maven 3.2+`
- `SpringBoot 2.1.2.RELEASE`
- `SpringCloud Greenwich.RELEASE`
- `guava 28.2-jre`
- 测服环境的redis服务器配置未知
- 
## 前置工作

pom文件引入

```
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>28.2-jre</version>
        </dependency>
```


## 代码实现

### 布隆过滤器核心配置

```
/**
 * 布隆过滤器核心配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/19 15:16
 */
@Configurable
public class BloomFilterHelper<T> {

    private int numHashFunctions;
    private int bitSize;
    private Funnel<T> funnel;

    public BloomFilterHelper(int expectedInsertions) {
        this.funnel = (Funnel<T>) Funnels.stringFunnel(Charset.defaultCharset());
        bitSize = optimalNumOfBits(expectedInsertions, 0.03);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        this.funnel = funnel;
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

}
```

### 布隆过滤器


```
/**
 * 布隆过滤器
 *
 * @author :    zhangquansheng
 * @date :    2020/1/19 15:17
 */
@Component
public class RedisBloomFilter<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据给定的布隆过滤器添加值，在添加一个元素的时候使用，批量添加的性能差
     *
     * @param bloomFilterHelper 布隆过滤器对象
     * @param key               KEY
     * @param value             值
     * @param <T>               泛型，可以传入任何类型的value
     */
    public <T> void add(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器添加值，在添加一批元素的时候使用，批量添加的性能好，使用pipeline方式(如果是集群下，请使用优化后RedisPipeline的操作)
     *
     * @param bloomFilterHelper 布隆过滤器对象
     * @param key               KEY
     * @param valueList         值，列表
     * @param <T>               泛型，可以传入任何类型的value
     */
    public <T> void addList(BloomFilterHelper<T> bloomFilterHelper, String key, List<T> valueList) {
        redisTemplate.executePipelined(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (T value : valueList) {
                    int[] offset = bloomFilterHelper.murmurHashOffset(value);
                    for (int i : offset) {
                        connection.setBit(key.getBytes(), i, true);
                    }
                }
                return null;
            }
        });
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     *
     * @param bloomFilterHelper 布隆过滤器对象
     * @param key               KEY
     * @param value             值
     * @param <T>               泛型，可以传入任何类型的value
     * @return 是否存在
     */
    public <T> boolean contains(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }
        return true;
    }

}
```

### 测试

#### 使用add添加元素

- 核心代码
```
        BloomFilterHelper<CharSequence> bloomFilterHelper = new BloomFilterHelper(10000);
        redisBloomFilter.delete("bloom");
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            redisBloomFilter.add(bloomFilterHelper, "bloom", i + "");
        }
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("布隆过滤器添加{}个值，耗时：{}ms", 1000, costMs);
```
- 日志打印
```
15:54:58.234 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 布隆过滤器添加1000个值，耗时：31326ms
```
可以看到，这个性能是很差的，在只添加1000个值得情况下，就需要31s，这是不可接受的！
> 查看源码发现使用RedisTemlate进行基本操作时，每次操作都需要拿到connection然后再进行操作，由于redis是单线程的，下一次请求必须等待上一次请求执行完成后才能继续执行，所以这种批量操作时就会很慢。

DefaultValueOperations中setBit实现
```
	@Override
	public Boolean setBit(K key, long offset, boolean value) {

		byte[] rawKey = rawKey(key);
		return execute(connection -> connection.setBit(rawKey, offset, value), true);
	}
```

execute
```
   /**
	 * Executes the given action object within a connection that can be exposed or not. Additionally, the connection can
	 * be pipelined. Note the results of the pipeline are discarded (making it suitable for write-only scenarios).
	 *
	 * @param <T> return type
	 * @param action callback object to execute
	 * @param exposeConnection whether to enforce exposure of the native Redis Connection to callback code
	 * @param pipeline whether to pipeline or not the connection for the execution
	 * @return object returned by the action
	 */
	@Nullable
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {

		Assert.isTrue(initialized, "template not initialized; call afterPropertiesSet() before using it");
		Assert.notNull(action, "Callback object must not be null");

		RedisConnectionFactory factory = getRequiredConnectionFactory();
		RedisConnection conn = null;
		try {

			if (enableTransactionSupport) {
				// only bind resources in case of potential transaction synchronization
				conn = RedisConnectionUtils.bindConnection(factory, enableTransactionSupport);
			} else {
				conn = RedisConnectionUtils.getConnection(factory);
			}

			boolean existingConnection = TransactionSynchronizationManager.hasResource(factory);

			RedisConnection connToUse = preProcessConnection(conn, existingConnection);

			boolean pipelineStatus = connToUse.isPipelined();
			if (pipeline && !pipelineStatus) {
				connToUse.openPipeline();
			}

			RedisConnection connToExpose = (exposeConnection ? connToUse : createRedisConnectionProxy(connToUse));
			T result = action.doInRedis(connToExpose);

			// close pipeline
			if (pipeline && !pipelineStatus) {
				connToUse.closePipeline();
			}

			// TODO: any other connection processing?
			return postProcessResult(result, connToUse, existingConnection);
		} finally {
			RedisConnectionUtils.releaseConnection(conn, factory);
		}
	}
```






### 使用addList批量添加元素

基于性能的考虑，考虑使用redisTemplate所提供pipeline相关接口来实现批量添加。

- 核心代码

```
   /**
     * 根据给定的布隆过滤器添加值，在添加一批元素的时候使用，批量添加的性能好，使用pipeline方式(如果是集群下，请使用优化后RedisPipeline的操作)
     *
     * @param bloomFilterHelper 布隆过滤器对象
     * @param key               KEY
     * @param valueList         值，列表
     * @param <T>               泛型，可以传入任何类型的value
     */
    public <T> void addList(BloomFilterHelper<T> bloomFilterHelper, String key, List<T> valueList) {
        redisTemplate.executePipelined(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (T value : valueList) {
                    int[] offset = bloomFilterHelper.murmurHashOffset(value);
                    for (int i : offset) {
                        connection.setBit(key.getBytes(), i, true);
                    }
                }
                return null;
            }
        });
    }
```

- 日志打印
```
16:30:51.451 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 声明过滤器的时候 size 为1000000，错误率设置 0.001
16:30:52.306 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 布隆过滤器添加1000个值，耗时：499ms
```
性能提升明显，继续测试`100000`个数据的耗时，日志打印如下:

```
16:38:13.884 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 声明过滤器的时候 size 为1000000，错误率设置 0.001
16:38:52.788 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 布隆过滤器添加100000个值，耗时：38500ms
```
> 并不是所有场景都适合使用pipeline方式，有些系统可能对可靠性要求很高，每次操作都需要立马知道这次操作是否成功，是否数据已经写进redis了，这种场景就不适合了。像我这种需求就是对实时性没有那么强，对每条数据不需要马上知道结果，所以比较适用。

### 使用contains验证结果

- 测试代码
```
        int expectedInsertions = 1000;
        double fpp = 0.1;
        redisBloomFilter.delete("bloom");
        BloomFilterHelper<CharSequence> bloomFilterHelper = new BloomFilterHelper<>(Funnels.stringFunnel(Charset.defaultCharset()), expectedInsertions, fpp);
        int j = 0;
        // 添加100个元素
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            valueList.add(i + "");
        }
        long beginTime = System.currentTimeMillis();
        redisBloomFilter.addList(bloomFilterHelper, "bloom", valueList);
        long costMs = System.currentTimeMillis() - beginTime;
        log.info("布隆过滤器添加{}个值，耗时：{}ms", 100, costMs);
        for (int i = 0; i < 1000; i++) {
            boolean result = redisBloomFilter.contains(bloomFilterHelper, "bloom", i + "");
            if (!result) {
                j++;
            }
        }
        log.info("漏掉了{}个,验证结果耗时：{}ms", j, System.currentTimeMillis() - beginTime);
```

- 日志打印
```
09:26:54.184 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 布隆过滤器添加100个值，耗时：168ms
09:27:12.525 [restartedMain] INFO  com.gaodun.storm.vod.bloom.BloomFilterRunner - 漏掉了900个,验证结果耗时：18510ms
```


## 布隆过滤器应用

在实际工作中，布隆过滤器常见的应用场景如下：

- 网页爬虫对 URL 去重，避免爬取相同的 URL 地址；
- 反垃圾邮件，从数十亿个垃圾邮件列表中判断某邮箱是否垃圾邮箱；
- Google Chrome 使用布隆过滤器识别恶意 URL；
- Medium 使用布隆过滤器避免推荐给用户已经读过的文章；
- Google BigTable，Apache HBbase 和 Apache Cassandra 使用布隆过滤器减少对不存在的行和列的查找。 除了上述的应用场景之外，布隆过滤器还有一个应用场景就是解决缓存穿透的问题。所谓的缓存穿透就是服务调用方每次都是查询不在缓存中的数据，这样每次服务调用都会到数据库中进行查询，如果这类请求比较多的话，就会导致数据库压力增大，这样缓存就失去了意义。


## 总结
- 本文参考了guava BloomFilter布隆过滤器实现
- 误判率 fpp 的值越小，匹配的精度越高。当减少误判率 fpp 的值，需要的存储空间也越大，所以在实际使用过程中需要在误判率和存储空间之间做个权衡


