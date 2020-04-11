package com.zhengcheng.db.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Objects;

/**
 * ReadOnlyConnection annotation Aspect
 *
 * @author :    quansheng.zhang
 * @date :    2019/11/28 16:04
 */
@Slf4j
@Aspect
@ConditionalOnClass({DataSource.class})
public class ReadOnlyConnectionAspect {

    @Autowired
    DataSource dataSource;

    @Pointcut("@annotation(com.zhengcheng.db.annotation.ReadOnlyConnection)")
    public void readOnlyConnectionPointcut() {
    }

    @Around("readOnlyConnectionPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Connection connection = dataSource.getConnection();
        if (Objects.nonNull(connection)) {
            connection.setReadOnly(true);
        }
        return pjp.proceed();
    }

}
