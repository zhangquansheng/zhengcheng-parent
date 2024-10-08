package com.zhengcheng.ums.domain.model;

import com.zhengcheng.ums.domain.entity.SysRoleResourceEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleAuth {

    /**
     * 角色id
     */
    Long roleID;

    /**
     * 权限字符
     */
    String perms;

    /**
     * 资源编码
     */
    String resourceCode;

    public SysRoleAuth(SysRoleResourceEntity roleResourceEntity) {
        this.roleID = roleResourceEntity.getRoleId();
        this.resourceCode = roleResourceEntity.getResourceCode();
    }
}
