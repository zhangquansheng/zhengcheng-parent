package com.zhengcheng.aliyun;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.zhengcheng.aliyun.mq.properties.ProducerProperties;
import com.zhengcheng.aliyun.mq.runner.ProducerRunner;
import com.zhengcheng.aliyun.properties.AliyunProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Producer Auto Configuration
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Configuration
@EnableConfigurationProperties({AliyunProperties.class, ProducerProperties.class})
@ConditionalOnProperty(
        prefix = "aliyun.mq.producer",
        name = "id"
)
public class ProducerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Producer producer(AliyunProperties aliyunProperties, ProducerProperties producerProperties) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, aliyunProperties.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey, aliyunProperties.getAccessKeySecret());
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, String.valueOf(producerProperties.getSendTimeout()));
        return ONSFactory.createProducer(properties);
    }

    @Bean
    public ProducerRunner producerRunner(ApplicationContext applicationContext) {
        ProducerRunner producerRunner = new ProducerRunner();
        producerRunner.setApplicationContext(applicationContext);
        return producerRunner;
    }
}
