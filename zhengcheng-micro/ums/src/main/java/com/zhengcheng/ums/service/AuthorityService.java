package com.zhengcheng.ums.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.ums.domain.entity.Authority;

/**
 * 权限表(Authority)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:57
 */
public interface AuthorityService extends IService<Authority> {

    /**
     * 查询权限编码列表
     * @param loginId 用户ID
     * @param loginType 登录类型
     * @return 权限编码列表
     */
    List<String> getPermissionList(Object loginId, String loginType);

    /**
     * 查询用户的权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Authority> getAuthorityList(Long userId);

    /**
     * 查询角色的权限列表
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Authority> getAuthoritiesByRoleId(Long roleId);
}
