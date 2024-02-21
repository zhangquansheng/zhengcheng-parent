package com.zhengcheng.expression;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ExpressionAutoConfiguration
 *
 * @author quansheng1.zhang
 * @since 2024/2/21 9:44
 */
@Configuration
public class ExpressionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KeyResolver keyResolver() {
        return new SpelExpressionKeyResolver();
    }
}
