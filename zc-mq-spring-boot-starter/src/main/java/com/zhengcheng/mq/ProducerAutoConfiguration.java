package com.zhengcheng.mq;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.zhengcheng.mq.properties.ProducerProperties;
import com.zhengcheng.mq.runner.ProducerRunner;
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
@EnableConfigurationProperties(ProducerProperties.class)
@ConditionalOnProperty(
        prefix = "mq.producer",
        name = "id"
)
public class ProducerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Producer producer(ProducerProperties producerProperties) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId, producerProperties.getId());
        properties.setProperty(PropertyKeyConst.AccessKey, producerProperties.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey, producerProperties.getSecretKey());
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
