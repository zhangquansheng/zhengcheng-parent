package com.zhengcheng.leaf;

import com.zhengcheng.leaf.exception.InitException;
import com.zhengcheng.leaf.properties.LeafProperties;
import com.zhengcheng.leaf.properties.LeafSegmentProperties;
import com.zhengcheng.leaf.properties.LeafSnowflakeProperties;
import com.zhengcheng.leaf.service.SegmentService;
import com.zhengcheng.leaf.service.SnowflakeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Leaf 自动配置
 *
 * @author quansheng1.zhang
 * @since 2020/12/24 13:01
 */
@Configuration
@EnableConfigurationProperties({LeafSnowflakeProperties.class, LeafSegmentProperties.class, LeafProperties.class})
public class LeafAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SnowflakeService.class)
    public SnowflakeService snowflakeService(LeafSnowflakeProperties leafSnowflakeProperties, LeafProperties leafProperties) throws InitException {
        return new SnowflakeService(leafSnowflakeProperties, leafProperties);
    }

    @Bean
    @ConditionalOnMissingBean(SegmentService.class)
    public SegmentService segmentService(LeafSegmentProperties leafSegmentProperties) throws InitException {
        return new SegmentService(leafSegmentProperties);
    }

}
