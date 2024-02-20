package com.zhengcheng.leaf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LeafSegmentProperties
 *
 * @author quansheng1.zhang
 * @since 2020/12/24 15:34
 */
@Data
@ConfigurationProperties(prefix = "leaf.segment")
public class LeafSegmentProperties {

    /**
     * 是否启用
     */
    private boolean enable = true;

    /**
     * jdbc属性配置
     */
    private JDBCProperties jdbc;

    @Data
    public static class JDBCProperties {

        /**
         * URL
         */
        private String url;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;
    }

}
