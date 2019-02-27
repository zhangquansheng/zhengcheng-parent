package com.zhengcheng.boot.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SwaggerProperties
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.boot.swagger
 * @Description :
 * @date :    2019/2/2 15:52
 */
@Data
@ConfigurationProperties(
        prefix = "spring.swagger"
)
public class SwaggerProperties {

    private String groupName;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String license;
    private String licenseUrl;
    private String version;
    private Boolean enable;
    private String basePackage;
}
