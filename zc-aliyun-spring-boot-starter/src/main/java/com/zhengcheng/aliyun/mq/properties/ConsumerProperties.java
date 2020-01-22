package com.zhengcheng.aliyun.mq.properties;

import com.aliyun.openservices.ons.api.PropertyValueConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * ConsumerProperties
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/13 0:17
 */
@Data
@ConfigurationProperties(prefix = "aliyun.mq.consumer")
public class ConsumerProperties {
    /**
     * 您在控制台创建的 Group ID
     */
    private String id;
    /**
     * 集群订阅方式 (默认)
     */
    private String messageModel = PropertyValueConst.CLUSTERING;
    /**
     * 设置 TCP 接入域名，进入控制台的实例管理页面的“获取接入点信息”区域查看
     */
    private String namesrvAddr;
    /**
     * 动态订阅
     */
    private List<SubscriptionTable> subscriptions = new ArrayList<>();
}
