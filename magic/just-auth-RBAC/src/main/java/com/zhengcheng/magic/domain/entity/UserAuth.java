package com.zhengcheng.magic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户授权表(UserAuth)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_auth")
public class UserAuth extends BaseEntity<UserAuth> {
    private static final long serialVersionUID = -95969397947662980L;
    /**
     * 用户ID(user表ID)
     */
    private Long userId;
    /**
     * 社会化用户ID(social_user表ID)
     */
    private Long socialUserId;

}
