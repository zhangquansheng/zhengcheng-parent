package com.zhengcheng.tk.mybatis;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * tk.mybatis 配置
 *
 * @author :    quansheng.zhang
 * @date :    2020/10/15 17:31
 */
@EnableTransactionManagement
@MapperScan(basePackages = "com.zhengcheng.**.mapper*")
@Configuration
public class TkMybatisAutoConfiguration {

}
