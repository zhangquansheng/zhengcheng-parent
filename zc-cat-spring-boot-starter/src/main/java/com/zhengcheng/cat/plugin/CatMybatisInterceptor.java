package com.zhengcheng.cat.plugin;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * CatMybatisInterceptor CAT集成
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/5 21:20
 */
@Intercepts({
        @Signature(method = "query", type = Executor.class, args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class}),
        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})
})
public class CatMybatisInterceptor implements Interceptor {

    private static final Pattern PARAMETER_PATTERN = Pattern.compile("\\?");
    private static final String MYSQL_DEFAULT_URL = "jdbc:mysql://UUUUUKnown:3306/%s?useUnicode=true";
    Executor target;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = this.getStatement(invocation);
        String methodName = this.getMethodName(mappedStatement);
        Transaction t = Cat.newTransaction("SQL", methodName);

        String sql = this.getSql(invocation, mappedStatement);
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Cat.logEvent("SQL.Method", sqlCommandType.name().toLowerCase(), Message.SUCCESS, sql);

        String url = this.getSQLDatabaseUrlByStatement(mappedStatement);
        Cat.logEvent("SQL.Database", url);

        return doFinish(invocation, t);
    }

    private MappedStatement getStatement(Invocation invocation) {
        return (MappedStatement) invocation.getArgs()[0];
    }

    private String getMethodName(MappedStatement mappedStatement) {
        String[] strArr = mappedStatement.getId().split("\\.");
        return strArr[strArr.length - 2] + "." + strArr[strArr.length - 1];
    }

    private String getSql(Invocation invocation, MappedStatement mappedStatement) {
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        return sqlResolve(configuration, boundSql);
    }

    private Object doFinish(Invocation invocation, Transaction t) throws InvocationTargetException, IllegalAccessException {
        try {
            Object returnObj = invocation.proceed();
            t.setStatus(Transaction.SUCCESS);
            return returnObj;
        } catch (Exception e) {
            Cat.logError(e);
            throw e;
        } finally {
            t.complete();
        }
    }


    private String getSQLDatabaseUrlByStatement(MappedStatement mappedStatement) {
        DataSource dataSource = null;
        try {
            Configuration configuration = mappedStatement.getConfiguration();
            Environment environment = configuration.getEnvironment();
            dataSource = environment.getDataSource();

            return switchDataSource(dataSource);
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            Cat.logError(e);
        }
        if (dataSource != null && dataSource.getClass() != null) {
            Cat.logError(new Exception("UnSupport type of DataSource : " + dataSource.getClass().toString()));
        }
        return MYSQL_DEFAULT_URL;
    }

    private String switchDataSource(DataSource dataSource) throws NoSuchFieldException, IllegalAccessException {
        String url = null;

        if (dataSource instanceof PooledDataSource) {
            Field dataSource1 = dataSource.getClass().getDeclaredField("dataSource");
            dataSource1.setAccessible(true);
            UnpooledDataSource dataSource2 = (UnpooledDataSource) dataSource1.get(dataSource);
            url = dataSource2.getUrl();
        }

        return url;
    }

    private String sqlResolve(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(resolveParameterValue(parameterObject)));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                Matcher matcher = PARAMETER_PATTERN.matcher(sql);
                StringBuffer sqlBuffer = new StringBuffer();
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    Object obj = null;
                    if (metaObject.hasGetter(propertyName)) {
                        obj = metaObject.getValue(propertyName);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        obj = boundSql.getAdditionalParameter(propertyName);
                    }
                    if (matcher.find()) {
                        matcher.appendReplacement(sqlBuffer, Matcher.quoteReplacement(resolveParameterValue(obj)));
                    }
                }
                matcher.appendTail(sqlBuffer);
                sql = sqlBuffer.toString();
            }
        }
        return sql;
    }

    private String resolveParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format((Date) obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            this.target = (Executor) target;
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
