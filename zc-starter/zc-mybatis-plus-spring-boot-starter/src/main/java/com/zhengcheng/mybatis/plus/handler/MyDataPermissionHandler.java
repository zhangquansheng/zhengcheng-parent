package com.zhengcheng.mybatis.plus.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * MyDataPermissionHandler
 *
 * @author quansheng1.zhang
 * @since 2023/1/19 10:12
 */
public class MyDataPermissionHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        Map<String, Object> conditions = new ConcurrentHashMap<>();
//        conditions.put("aaaa", 11212);
        return dataScopeFilter(where, conditions);
    }


    public static Expression dataScopeFilter(Expression where, Map<String, Object> conditions) {
        //定义条件
        AtomicReference<Expression> whereAtomic = new AtomicReference<>(where);
        //循环构造条件
        conditions.forEach((key, value) -> {
            //判断value的类型（集合特殊处理）
            if (value instanceof Collection) {
                Collection<?> collection = (Collection<?>) value;
                InExpression expression = new InExpression();
                expression.setLeftExpression(new Column(key));
                //获取条件
                ItemsList itemsList = new ExpressionList(collection.stream().map(String::valueOf).map(StringValue::new).collect(Collectors.toList()));
                expression.setRightItemsList(itemsList);
                //拼接条件
                whereAtomic.set(new AndExpression(whereAtomic.get(), expression));
            } else {
                whereAtomic.set(new AndExpression(whereAtomic.get(), new EqualsTo(new Column(key), new StringValue(String.valueOf(value)))));
            }
        });

        return whereAtomic.get();
    }
}
