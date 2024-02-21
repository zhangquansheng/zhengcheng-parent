package com.zhengcheng.ums.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.ums.domain.entity.UserRole;

/**
 * 用户角色表(UserRole)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:58
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 获取角色编码列表
     * 
     * @param userId
     *            用户ID
     * @return 角色编码列表
     */
    List<String> getRoleCodeList(Long userId);

    /**
     * 添加、更新用户角色
     * 
     * @param userRoles
     *            用户角色
     */
    void save(List<UserRole> userRoles);
}
