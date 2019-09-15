package com.zhengcheng.swagger.mq.properties;

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
@ConfigurationProperties(prefix = "mq.consumer")
public class ConsumerProperties {
    /**
     * 您在控制台创建的 Group ID
     */
    private String id;
    /**
     * AccessKeyId 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    private String accessKey;
    /**
     * AccessKeySecret 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    private String secretKey;
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
