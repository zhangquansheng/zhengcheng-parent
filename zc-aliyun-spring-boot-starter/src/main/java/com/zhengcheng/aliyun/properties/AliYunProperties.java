package com.zhengcheng.aliyun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云主账号配置
 *
 * @author :    zhangquansheng
 * @date :    2020/1/22 9:18
 */
@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliYunProperties {
    /**
     * 密钥key
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String accessKey;
    /**
     * 密钥密码
     */
    private String accessKeySecret;
    /**
     * 区域ID，访问regionId支持: cn-shanghai,cn-beijing,ap-southeast-1, us-west-1, 其他区域暂不支持, 请勿使用
     */
    private String regionId;
    /**
     * 端点，Endpoint以杭州（http://oss-cn-hangzhou.aliyuncs.com）为例，其它Region请按实际情况填写
     */
    private String endpoint;
}