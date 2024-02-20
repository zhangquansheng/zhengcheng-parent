package com.zhengcheng.rocket.mq.properties;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Objects;
import java.util.Properties;

/**
 * Rocketmq 属性
 *
 * @author quansheng1.zhang
 * @since 2020/10/19 14:43
 */
@RefreshScope
@Data
public class RocketMQProperties {

    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;
    private String topic;
    private String groupId;
    private String tag;
    private String orderTopic;
    private String orderGroupId;
    private String orderTag;

    public Properties getMqProperties(AliyunAkProperties aliyunAkProperties) {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, Objects.equals(aliyunAkProperties.getAkEnabled(), Boolean.FALSE) ? this.accessKey : aliyunAkProperties.getAk());
        properties.setProperty(PropertyKeyConst.SecretKey, Objects.equals(aliyunAkProperties.getAkEnabled(), Boolean.FALSE) ? this.secretKey : aliyunAkProperties.getSec());
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }

    public Properties getMqConsumerProperties(AliyunAkProperties aliyunAkProperties) {
        //配置文件
        Properties properties = this.getMqProperties(aliyunAkProperties);
        properties.setProperty(PropertyKeyConst.GROUP_ID, this.getGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        return properties;
    }

}
