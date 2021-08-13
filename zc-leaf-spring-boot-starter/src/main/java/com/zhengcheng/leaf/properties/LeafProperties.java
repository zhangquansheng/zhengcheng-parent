package com.zhengcheng.leaf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LeafProperties
 *
 * @author quansheng1.zhang
 * @since 2020/12/24 15:34
 */
@Data
@ConfigurationProperties(prefix = "leaf")
public class LeafProperties {

    /**
     * 名称
     */
    private String name;

}
