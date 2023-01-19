package com.zhengcheng.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.mybatis.plus.config.BaseMetaObjectHandler;
import com.zhengcheng.mybatis.plus.handler.MyDataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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
@Import(BaseMetaObjectHandler.class)
public class MybatisPlusAutoConfiguration {

    public MybatisPlusAutoConfiguration() {
        log.info("------ mybatis-plus 自动配置  ---------------------------------------");
        log.info("------ 事务管理注解 @EnableTransactionManagement 配置成功 ");
        log.info("------ 数据库表公共字段：id、is_deleted、create_user_id、create_time、update_user_id、update_time ");
        log.info("------ MapperScan com.zhengcheng.**.mapper* ");
        log.info("-----------------------------------------------------------------");
    }

//    /**
//     * 分页插件，自动识别数据库类型(旧版)
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        paginationInterceptor.setLimit(CommonConstants.DEFAULT_PAGINATION_LIMIT);
//        return paginationInterceptor;
//    }
//
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
//        return new OptimisticLockerInterceptor();
//    }

    // 最新版
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 数据权限插件
//        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(new MyDataPermissionHandler());
//        interceptor.addInnerInterceptor(dataPermissionInterceptor);

        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(CommonConstants.DEFAULT_PAGINATION_LIMIT);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
