package com.zhengcheng.swagger.mq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.zhengcheng.swagger.mq.factory.ConsumerFactory;
import com.zhengcheng.swagger.mq.properties.ConsumerProperties;
import com.zhengcheng.swagger.mq.runner.ConsumerRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Properties;

/**
 * Consumer Auto Configuration
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Configuration
@EnableConfigurationProperties({ConsumerProperties.class})
@ConditionalOnProperty(
        prefix = "mq.consumer",
        name = "id"
)
@Import({ConsumerFactory.class})
public class ConsumerAutoConfiguration {

    @Bean
    public Consumer consumer(ConsumerProperties consumerProperties) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, consumerProperties.getId());
        properties.setProperty(PropertyKeyConst.AccessKey, consumerProperties.getAccessKey());
        properties.setProperty(PropertyKeyConst.SecretKey, consumerProperties.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, consumerProperties.getNamesrvAddr());
        properties.setProperty(PropertyKeyConst.MessageModel, consumerProperties.getMessageModel());
        return ONSFactory.createConsumer(properties);
    }

    @Bean
    public ConsumerRunner consumerRunner(ApplicationContext applicationContext) {
        ConsumerRunner consumerRunner = new ConsumerRunner();
        consumerRunner.setApplicationContext(applicationContext);
        return consumerRunner;
    }
}
