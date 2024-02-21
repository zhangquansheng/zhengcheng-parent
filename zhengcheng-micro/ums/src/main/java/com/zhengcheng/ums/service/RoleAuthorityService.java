package com.zhengcheng.ums.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.ums.domain.entity.RoleAuthority;

/**
 * 角色权限表(RoleAuthority)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:57:02
 */
public interface RoleAuthorityService extends IService<RoleAuthority> {

    /**
     * 根据权限编码列表
     *
     * @param userId
     *            用户ID
     * @return 权限编码
     */
    List<String> getAuthorityCodeList(Long userId);

    /**
     * 删除原有角色权限，批量插入新的角色权限
     * 
     * @param roleAuthorities
     *            角色权限列表
     */
    void save(List<RoleAuthority> roleAuthorities);
}
