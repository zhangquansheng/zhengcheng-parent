package com.zhengcheng.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色表(Role)实体类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role")
public class Role extends BaseEntity<Role> {
    private static final long serialVersionUID = -97897294551047190L;
    /**
     * 名称
     */
    private String            name;
    /**
     * 角色编码
     */
    private String            code;
    /**
     * 是否内置
     */
    @TableField(value = "is_system")
    private Boolean           system;
    /**
     * 描述
     */
    private String            description;
    /**
     * 是否启用
     */
    @TableField(value = "is_enable")
    private Boolean           enable;
}
