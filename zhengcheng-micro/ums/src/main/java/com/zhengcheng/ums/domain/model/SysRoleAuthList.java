package com.zhengcheng.ums.domain.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleAuthList {

    /**
     * 角色id
     */
    Long roleID;

    /**
     * 权限字符
     */
    Set<String> perms;

    /**
     * 资源编码
     */
    Set<String> resourceCode;
}
