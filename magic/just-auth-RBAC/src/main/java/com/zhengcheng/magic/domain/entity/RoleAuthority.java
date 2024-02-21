package com.zhengcheng.magic.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色权限表(RoleAuthority)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:57:02
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role_authority")
public class RoleAuthority extends BaseEntity<RoleAuthority> {
    private static final long serialVersionUID = 258741929006361459L;
    /**
     * 角色ID（role表ID）
     */
    private Long roleId;
    /**
     * 权限ID（authority表ID）
     */
    private Long authorityId;

}
