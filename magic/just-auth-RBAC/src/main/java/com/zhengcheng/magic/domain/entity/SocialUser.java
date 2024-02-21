package com.zhengcheng.magic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 社会化用户表(SocialUser)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 16:57:01
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("social_user")
public class SocialUser extends BaseEntity<SocialUser> {
    private static final long serialVersionUID = 702994897432509294L;
    /**
     * 第三方系统的唯一ID
     */
    private String uuid;
    /**
     * 第三方用户来源,GITHUB、GITEE、QQ，更多请参考：AuthDefaultSource.java
     */
    private String source;
    /**
     * 用户的授权令牌
     */
    private String accessToken;
    /**
     * 第三方用户的授权令牌的有效期,部分平台可能没有
     */
    private Integer expireIn;
    /**
     * 刷新令牌,部分平台可能没有
     */
    private String refreshToken;
    /**
     * 第三方用户的 open id,部分平台可能没有
     */
    private String openId;
    /**
     * 第三方用户的 ID,部分平台可能没有
     */
    private String uid;
    /**
     * 个别平台的授权信息,部分平台可能没有
     */
    private String accessCode;
    /**
     * 第三方用户的 union id,部分平台可能没有
     */
    private String unionId;
    /**
     * 第三方用户授予的权限,部分平台可能没有
     */
    private String scope;
    /**
     * 个别平台的授权信息,部分平台可能没有
     */
    private String tokenType;
    /**
     * id token 部分平台可能没有
     */
    private String idToken;
    /**
     * 小米平台用户的附带属性,部分平台可能没有
     */
    private String macAlgorithm;
    /**
     * 小米平台用户的附带属性,部分平台可能没有
     */
    private String macKey;
    /**
     * 用户的授权code,部分平台可能没有
     */
    private String code;
    /**
     * Twitter平台用户的附带属性,部分平台可能没有
     */
    private String oauthToken;
    /**
     * Twitter平台用户的附带属性,部分平台可能没有
     */
    private String oauthTokenSecret;

}
