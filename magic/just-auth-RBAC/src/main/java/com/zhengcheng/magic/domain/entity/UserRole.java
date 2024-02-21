package com.zhengcheng.magic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户角色表(UserRole)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:57
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_role")
public class UserRole extends BaseEntity<UserRole> {
    private static final long serialVersionUID = -36322324757580472L;
    /**
     * 用户ID(user表ID)
     */
    private Long userId;
    /**
     * 角色ID(role表ID)
     */
    private Long roleId;

}
