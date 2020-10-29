package com.zhengcheng.mybatis.plus;

import com.zhengcheng.mybatis.plus.aspect.ReadOnlyConnectionAspect;
import com.zhengcheng.mybatis.plus.config.DateMetaObjectHandler;
import com.zhengcheng.mybatis.plus.config.DefaultMybatisPlusConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.zhengcheng.**.mapper*")
@Import({ReadOnlyConnectionAspect.class, DateMetaObjectHandler.class})
public class MybatisPlusAutoConfiguration extends DefaultMybatisPlusConfig {

    public MybatisPlusAutoConfiguration() {
        log.info("------ mybatis-plus 自动配置  ---------------------------------------");
        log.info("------ 事务管理注解 @EnableTransactionManagement 配置成功 ");
        log.info("------ 数据库表公共字段：id、gmt_create、gmt_modified、is_deleted ");
        log.info("------ MapperScan com.zhengcheng.**.mapper* ");
        log.info("-----------------------------------------------------------------");
    }

}
