package com.zhengcheng.leaf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LeafSnowflakeProperties
 *
 * @author quansheng1.zhang
 * @since 2020/12/24 14:02
 */
@Data
@ConfigurationProperties(prefix = "leaf.snowflake")
public class LeafSnowflakeProperties {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * zk地址
     */
    private String zkAddress;

    /**
     * zk端口号
     */
    private Integer port;
}
