package com.zhengcheng.rocket.mq;

import cn.hutool.core.util.StrUtil;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.zhengcheng.rocket.mq.annotation.RocketMQListener;
import com.zhengcheng.rocket.mq.properties.AliyunAkProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;

import java.util.Properties;

/**
 * ProducerAutoConfiguration
 *
 * @author quansheng1.zhang
 * @since 2021/2/3 13:19
 */
@Slf4j
@ConditionalOnBean({AliyunAkProperties.class, StandardEnvironment.class})
@Configuration
public class ProducerAutoConfiguration {

    @Autowired
    private AliyunAkProperties aliyunAkProperties;
    @Autowired
    private StandardEnvironment environment;

    public ProducerAutoConfiguration() {
    }

    @ConditionalOnMissingBean(ProducerBean.class)
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {
        ProducerBean producer = new ProducerBean();
        producer.setProperties(getMqProperties());
        log.info("RocketMQ buildProducer success,access-key:{}", aliyunAkProperties.getAk());
        return producer;
    }

    private Properties getMqProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, aliyunAkProperties.getAk());
        properties.setProperty(PropertyKeyConst.SecretKey, aliyunAkProperties.getSec());
        String namesrvAddr = environment.resolvePlaceholders(RocketMQListener.NAME_SRV_ADDR_PLACEHOLDER);
        if (StrUtil.isNotBlank(namesrvAddr)) {
            properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, namesrvAddr);
        }
        return properties;
    }
}
