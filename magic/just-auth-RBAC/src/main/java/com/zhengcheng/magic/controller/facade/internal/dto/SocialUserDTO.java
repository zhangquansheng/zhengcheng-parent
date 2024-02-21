package com.zhengcheng.magic.controller.facade.internal.dto;

import lombok.*;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 社会化用户表(SocialUser)数据传输对象
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 16:57:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialUserDTO implements Serializable {
    private static final long serialVersionUID = 599686075849770186L;
    @ApiModelProperty("第三方系统的唯一ID")
    private String uuid;
    @ApiModelProperty("第三方用户来源,GITHUB、GITEE、QQ，更多请参考：AuthDefaultSource.java")
    private String source;
    @ApiModelProperty("用户的授权令牌")
    private String accessToken;
    @ApiModelProperty("第三方用户的授权令牌的有效期,部分平台可能没有")
    private Integer expireIn;
    @ApiModelProperty("刷新令牌,部分平台可能没有")
    private String refreshToken;
    @ApiModelProperty("第三方用户的 open id,部分平台可能没有")
    private String openId;
    @ApiModelProperty("第三方用户的 ID,部分平台可能没有")
    private String uid;
    @ApiModelProperty("个别平台的授权信息,部分平台可能没有")
    private String accessCode;
    @ApiModelProperty("第三方用户的 union id,部分平台可能没有")
    private String unionId;
    @ApiModelProperty("第三方用户授予的权限,部分平台可能没有")
    private String scope;
    @ApiModelProperty("个别平台的授权信息,部分平台可能没有")
    private String tokenType;
    @ApiModelProperty("id token 部分平台可能没有")
    private String idToken;
    @ApiModelProperty("小米平台用户的附带属性,部分平台可能没有")
    private String macAlgorithm;
    @ApiModelProperty("小米平台用户的附带属性,部分平台可能没有")
    private String macKey;
    @ApiModelProperty("用户的授权code,部分平台可能没有")
    private String code;
    @ApiModelProperty("Twitter平台用户的附带属性,部分平台可能没有")
    private String oauthToken;
    @ApiModelProperty("Twitter平台用户的附带属性,部分平台可能没有")
    private String oauthTokenSecret;

}
