package com.zhengcheng.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zhengcheng.common.constant.CommonConstants;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * mybatis-plus配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.zhengcheng.**.mapper*"})
public class MybatisPlusConfig {

    public MybatisPlusConfig() {
        log.info("------ mybatis-plus 自动配置  ---------------------------------------");
        log.info("------ 事务管理注解 @EnableTransactionManagement 配置成功 ");
        log.info("------ 数据库表公共字段：id、is_deleted、create_user_id、create_time、update_user_id、update_time ");
        log.info("------ MapperScan com.zhengcheng.**.mapper* ");
        log.info("-----------------------------------------------------------------");
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(CommonConstants.DEFAULT_PAGINATION_LIMIT);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
