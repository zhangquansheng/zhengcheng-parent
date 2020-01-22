package com.zhengcheng.aliyun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云内容安全属性配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 22:42
 */
@Data
@ConfigurationProperties(prefix = "aliyun.acs")
public class AcsProperties {

}
