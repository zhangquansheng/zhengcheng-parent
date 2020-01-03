# Mysql数据库通用组件

## **简介**（Introduction）

基于[MybatisPlus](https://mp.baomidou.com/)

## **入门篇**

### **环境准备**

zhengcheng-parent 升级到最新版本，JDK1.8 ，数据库基于Mysql5.6以上

### **安装**

pom文件引入

```
  <dependency>
        <groupId>com.zhengcheng</groupId>
        <artifactId>zc-db-spring-boot-starter</artifactId>
  </dependency>
```

在启动类上加入Mapper扫码注解，注意修改成你的mapper路径
```
@MapperScan(basePackages = "com.zhengcheng.user.mapper*")
```

### **设置**

```
mybatis-plus.mapper-locations = classpath*:**/*Mapper.xml
mybatis-plus.type-aliases-package = com.zhengcheng.user.entity
mybatis-plus.configuration.map-underscore-to-camel-case = true
mybatis-plus.type-enums-package = com.zhengcheng.user.enums
```

> 更多设置请参考[MybatisPlus官方文档](https://mp.baomidou.com/)

## 多数据源配置整合

> 参考[动态数据源](https://mp.baomidou.com/guide/dynamic-datasource.html)

### 数据源配置

```
    @Bean(name = "masterHikariDataSource")
    @Qualifier("masterHikariDataSource")
    @ConfigurationProperties("spring.datasource.hikari.master")
    public DataSource masterHikariDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
```

```
/**
 * MybatisPlus 配置
 *
 * @author :    zhangquansheng
 * @date :    2019/12/24 13:16
 */
@Configuration
@MapperScan(basePackages = "com.zhengcheng.mapper*", sqlSessionTemplateRef = "masterSqlSessionTemplate")
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class MasterMybatisPlusConfig {

    private final DataSource dataSource;

    public MasterMybatisPlusConfig(@Qualifier("masterHikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        /**
         *  SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
         *  需要兼容mybatis-plus需要使用MybatisSqlSessionFactoryBean 代替 SqlSessionFactoryBean
         */
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeEnumsPackage("com.zhengcheng.enums");
        sqlSessionFactory.setTypeAliasesPackage("com.zhengcheng.entity");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:**/*Mapper.xml"));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        sqlSessionFactory.setConfiguration(configuration);

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new DefaultMetaObjectHandler());
        sqlSessionFactory.setGlobalConfig(globalConfig);

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(CommonConstants.DEFAULT_PAGINATION_LIMIT);
        sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor});
        return sqlSessionFactory.getObject();
    }

    @Bean("masterSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(masterSqlSessionFactory());
    }

    @Bean
    public PlatformTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
```

### 配置信息

```
spring.datasource.hikari.master.hikari.auto-commit=true
spring.datasource.hikari.master.hikari.connection-test-query=SELECT 1
spring.datasource.hikari.master.hikari.connection-timeout=30000
spring.datasource.hikari.master.hikari.idle-timeout=180000
spring.datasource.hikari.master.hikari.max-lifetime=1800000
spring.datasource.hikari.master.hikari.maximum-pool-size=10
spring.datasource.hikari.master.hikari.minimum-idle=5
spring.datasource.hikari.master.name=master
spring.datasource.hikari.master.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.hikari.master.jdbc-url=jdbc:mysql://127.0.0.1:3306/master?useSSL=false&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull
spring.datasource.hikari.master.username=root
spring.datasource.hikari.master.password=root
```

## **MySQL官方驱动**主从分离

> [Replication协议](https://dev.mysql.com/doc/refman/5.7/en/replication.html)

### 配置信息

```
# 改jdbc连接 jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8
spring.datasource.url=jdbc:mysql:replication://127.0.0.1:3306,127.0.0.1:3306,127.0.0.1:3306/zc-im?useUnicode=true&characterEncoding=UTF-8&autoReconnect=false&loadBalanceStrategy=random&autoReconnect=true&rewriteBatchedStatements=TRUE&zeroDateTimeBehavior=convertToNull
# 改驱动类 com.mysql.jdbc.Driver
spring.datasource.jdbc-driver=com.mysql.jdbc.ReplicationDriver
```

这种情况下 DataSource.getConnection() 获取的连接，实际上是ReplicationConnection，这个连接是虚拟的，和真实的数据库连接是个一对多的关系，所以记得给每一个MySQL都做上相应的机器授权。

如何来区别本次请求是读是写？其实是通过Connection中的readonly属性传递的。readonly=true的时，走从库查询。

对于Spring来说，就可以使用@Transactional注解来控制这个属性了。一个事务不可能跨两个连接，所以是读是写，有最高层决定。

```
  // 只读事务，走从库查询
  @Transactional(readOnly = true)
```

有些情况下，我们不需要为了一个读写分离，在复杂的查询中增加一个事务的开销，所以本项目中提供了@ReadOnlyConnection注解，作用是把Connection的readonly设置成true。




