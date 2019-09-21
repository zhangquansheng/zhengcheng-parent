package com.zhengcheng.db.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * mybatis-plus配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/28 21:31
 */
@Import(DateMetaObjectHandler.class)
public class DefaultMybatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 打印 sql，性能分析拦截器，不建议生产使用
     * 设置 dev test 环境开启
     */
    @Bean
    @Profile({"dev", "fat"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
}
