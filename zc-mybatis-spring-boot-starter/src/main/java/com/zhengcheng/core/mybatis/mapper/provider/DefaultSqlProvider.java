package com.zhengcheng.core.mybatis.mapper.provider;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.StrUtil;
import com.zhengcheng.core.mybatis.mapper.BaseMapper;
import com.zhengcheng.core.mybatis.metadata.TableInfo;
import com.zhengcheng.core.mybatis.metadata.TableInfoHelper;
import com.zhengcheng.core.mybatis.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.ResolvableType;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * 默认 SQL 提供者
 *
 * @author :    zhangquansheng
 * @date :    2020/3/28 14:45
 */
@Slf4j
public class DefaultSqlProvider<T extends BaseEntity> {

    private final static int MAX_SIZE = 1000;

    public String insert(BaseEntity baseEntity) {
        baseEntity.setGmtCreate(LocalDateTime.now());
        baseEntity.setGmtModified(LocalDateTime.now());

        TableInfo tableInfo = getTableInfo(baseEntity.getClass());
        SQL sql = new SQL();
        sql.INSERT_INTO(tableInfo.getTableName());
        tableInfo.getFieldList().forEach(tableFieldInfo -> {
            if (StrUtil.equals(tableFieldInfo.getColumn(), tableInfo.getKeyColumn())) {
                return;
            }
            sql.VALUES(String.format("`%s`", tableFieldInfo.getColumn()), String.format("#{%s}", tableFieldInfo.getProperty()));
        });
        return getSql(sql);
    }

    public String deleteById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(getEntityClass(context));
        SQL sql = new SQL();
        sql.DELETE_FROM(tableInfo.getTableName());
        sql.WHERE(String.format("`%s` = #{%s}", tableInfo.getKeyColumn(), tableInfo.getKeyProperty()));
        return getSql(sql);
    }

    public String deleteBatchIds(ProviderContext context, Collection<? extends Serializable> id) {
        TableInfo tableInfo = getTableInfo(getEntityClass(context));
        SQL sql = new SQL();
        sql.DELETE_FROM(tableInfo.getTableName());
        sql.WHERE(String.format("`%s` IN %s", tableInfo.getKeyColumn(), inExpression(tableInfo.getKeyProperty(), id.size())));
        return getSql(sql);
    }

    public String selectById(ProviderContext context) {
        TableInfo tableInfo = getTableInfo(getEntityClass(context));
        SQL sql = new SQL();
        sql.SELECT(tableInfo.getAllFieldString());
        sql.FROM(tableInfo.getTableName());
        sql.WHERE(String.format("`%s` = #{%s}", tableInfo.getKeyColumn(), tableInfo.getKeyProperty()));
        return sql.toString();
    }

    public String selectBatchIds(ProviderContext context, Collection<? extends Serializable> id) {
        SQL sql = new SQL();
        TableInfo tableInfo = getTableInfo(getEntityClass(context));
        sql.SELECT(tableInfo.getAllFieldString());
        sql.FROM(tableInfo.getTableName());
        sql.WHERE(String.format("`%s` IN %s", tableInfo.getKeyColumn(), inExpression(tableInfo.getKeyProperty(), id.size())));
        return getSql(sql);
    }

    public String updateById(BaseEntity baseEntity) {
        baseEntity.setGmtModified(LocalDateTime.now());

        TableInfo tableInfo = getTableInfo(baseEntity.getClass());
        SQL sql = new SQL();
        sql.UPDATE(tableInfo.getTableName());
        tableInfo.getFieldList().forEach(tableFieldInfo -> {
            if (StrUtil.equals(tableFieldInfo.getColumn(), tableInfo.getKeyColumn())) {
                return;
            }
            sql.SET(String.format("%s = #{%s}", tableFieldInfo.getColumn(), tableFieldInfo.getProperty()));
        });
        sql.WHERE(String.format("`%s` = #{%s}", tableInfo.getKeyColumn(), tableInfo.getKeyProperty()));
        return getSql(sql);
    }

    public String list(ProviderContext context) {
        SQL sql = new SQL();
        TableInfo tableInfo = getTableInfo(getEntityClass(context));
        sql.SELECT(tableInfo.getAllFieldString());
        sql.FROM(tableInfo.getTableName());
        sql.LIMIT(MAX_SIZE);
        return getSql(sql);
    }

    private String getSql(SQL sql) {
        String sqlStr = sql.toString();
        if (log.isDebugEnabled()) {
            log.debug("sql:[{}]", sqlStr);
        }
        return sqlStr;
    }

    private String inExpression(String property, int size) {
        MessageFormat messageFormat = new MessageFormat("#'{'" + property + "[{0}]}");
        StrBuilder sb = StrBuilder.create(" (");
        for (int i = 0; i < size; i++) {
            sb.append(messageFormat.format(new Object[]{i}));
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        return sb.append(")").toString();
    }

    private Class<?> getEntityClass(ProviderContext context) {
        Class<?> mapperType = context.getMapperType();
        for (Type parent : mapperType.getGenericInterfaces()) {
            ResolvableType parentType = ResolvableType.forType(parent);
            if (parentType.getRawClass() == BaseMapper.class) {
                return parentType.getGeneric(0).getRawClass();
            }
        }
        return null;
    }

    private TableInfo getTableInfo(Class<?> clazz) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        if (Objects.isNull(tableInfo)) {
            tableInfo = TableInfoHelper.initTableInfo(clazz);
        }
        return tableInfo;
    }


}
