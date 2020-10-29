package com.zhengcheng.core.aliyun.oss.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务端签名
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/2 17:11
 */
@Data
public class PostObjectPolicyDTO implements Serializable {

    private static final long serialVersionUID = 7309245303394982271L;
    /**
     * 用户请求的accessid
     */
    private String accessid;
    /**
     * 用户表单上传的策略（Policy），是经过base64编码过的字符串。详情请参见Post Policy。https://www.alibabacloud.com/help/zh/doc-detail/31988.htm?spm=a2c63.p38356.879954.10.21f72ebcERhP0S#section-d5z-1ww-wdb
     */
    private String policy;
    /**
     * 对变量policy签名后的字符串
     */
    private String signature;

    private String dir;
    /**
     * 用户要往哪个域名发送上传请求
     */
    private String host;
    /**
     * 上传策略Policy失效时间，在服务端指定。失效时间之前都可以利用此Policy上传文件，无需每次上传都去服务端获取签名。
     */
    private String expire;
}
