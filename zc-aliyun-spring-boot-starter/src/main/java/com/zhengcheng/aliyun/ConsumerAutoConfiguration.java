package com.zhengcheng.aliyun;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.zhengcheng.aliyun.mq.factory.ConsumerFactory;
import com.zhengcheng.aliyun.mq.properties.ConsumerProperties;
import com.zhengcheng.aliyun.mq.runner.ConsumerRunner;
import com.zhengcheng.aliyun.properties.AliYunProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@EnableConfigurationProperties({AliYunProperties.class, ConsumerProperties.class})
@ConditionalOnProperty(
        prefix = "aliyun.mq.consumer",
        name = "id"
)
@Import({ConsumerFactory.class})
public class ConsumerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Consumer consumer(AliYunProperties aliYunProperties, ConsumerProperties consumerProperties) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, consumerProperties.getId());
        properties.setProperty(PropertyKeyConst.AccessKey, aliYunProperties.getAccessKeyId());
        properties.setProperty(PropertyKeyConst.SecretKey, aliYunProperties.getAccessKeySecret());
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
