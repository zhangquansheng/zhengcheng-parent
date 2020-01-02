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

```
mybatis-plus.mapper-locations = classpath*:**/*Mapper.xml
mybatis-plus.type-aliases-package = com.zhengcheng.user.entity
mybatis-plus.configuration.map-underscore-to-camel-case = true
mybatis-plus.type-enums-package = com.zhengcheng.user.enums
```

> 更多设置请参考[MybatisPlus官方文档](https://mp.baomidou.com/)

## 多数据源配置整合

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


