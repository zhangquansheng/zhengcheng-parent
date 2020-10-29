package com.zhengcheng.core.mybatis.plus;

import com.zhengcheng.core.mybatis.plus.aspect.ReadOnlyConnectionAspect;
import com.zhengcheng.core.mybatis.plus.config.DefaultMybatisPlusConfig;
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
@EnableTransactionManagement
@MapperScan(basePackages = "com.zhengcheng.**.mapper*")
@Configuration
@Import({ReadOnlyConnectionAspect.class})
public class MybatisPlusAutoConfiguration extends DefaultMybatisPlusConfig {

}
