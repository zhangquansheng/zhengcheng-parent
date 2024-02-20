package com.zhengcheng.oss.properties;

import com.zhengcheng.common.constant.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 阿里云OSS属性
 *
 * @author :    quansheng.zhang
 * @date :    2019/10/3 13:40
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = CommonConstants.OSS_PREFIX)
public class OssProperties {
    /**
     * 密钥key
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String accessKeyId;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
    /**
     * 端点，Endpoint以杭州（http://oss-cn-hangzhou.aliyuncs.com）为例，其它Region请按实际情况填写
     */
    private String endpoint;
    /**
     * bucket名称
     */
    private String bucketName;
    /**
     * CDN域名，默认空
     */
    private String domain;
}
