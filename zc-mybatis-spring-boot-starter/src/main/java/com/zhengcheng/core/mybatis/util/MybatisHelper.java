package com.zhengcheng.core.mybatis.util;

import com.github.pagehelper.PageInterceptor;
import com.zhengcheng.core.mybatis.handlers.MybatisEnumTypeHandler;
import com.zhengcheng.core.mybatis.enums.IEnum;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * Mybatis 工具包
 *
 * @author :    zhangquansheng
 * @date :    2020/4/1 17:14
 */
public class MybatisHelper {

    /**
     * 注册枚举类型处理器
     *
     * @param sqlSessionFactoryBean SqlSessionFactoryBean
     * @param packageName           枚举的PackageName 例如：com.gaodun.com.gaodun.aladdin.domain.hermes.enums
     * @throws Exception 异常
     */
    public void registerEnumTypeHandler(SqlSessionFactoryBean sqlSessionFactoryBean, String packageName) throws Exception {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(IEnum.class), packageName);
        Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
        for (Class<?> clazz : handlerSet) {
            if (IEnum.class.isAssignableFrom(clazz) && !IEnum.class.equals(clazz)) {
                Objects.requireNonNull(sqlSessionFactoryBean.getObject()).getConfiguration().getTypeHandlerRegistry().register(clazz, MybatisEnumTypeHandler.class);
            }
        }
    }

    /**
     * 获取SqlSessionFactory，默认mysql并加入Pagehelper分页，
     *
     * @param dataSource         数据源
     * @param typeAliasesPackage model 的 PackageName 例如：com.gaodun.com.gaodun.aladdin.domain.hermes.model
     * @param typeEnumsPackage   枚举的PackageName 例如：com.gaodun.com.gaodun.aladdin.domain.hermes.enums
     * @param locationPattern    mybatis的xml文件本地路径
     * @return SqlSessionFactory
     * @throws Exception 异常
     */
    public SqlSessionFactory getMysqlPagehelperSqlSessionFactory(DataSource dataSource,
                                                                 String typeAliasesPackage,
                                                                 String typeEnumsPackage,
                                                                 String locationPattern) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        this.registerEnumTypeHandler(sqlSessionFactoryBean, typeEnumsPackage);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(locationPattern));
        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(interceptor);
        return sqlSessionFactoryBean.getObject();
    }

}
