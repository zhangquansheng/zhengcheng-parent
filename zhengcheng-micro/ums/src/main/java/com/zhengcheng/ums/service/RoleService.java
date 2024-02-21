package com.zhengcheng.ums.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.ums.domain.entity.Role;

/**
 * 角色表(Role)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询角色编码列表
     * @param loginId 用户ID
     * @param loginType 登录类型
     * @return 角色编码列表
     */
    List<String> getRoleList(Object loginId, String loginType);
}
