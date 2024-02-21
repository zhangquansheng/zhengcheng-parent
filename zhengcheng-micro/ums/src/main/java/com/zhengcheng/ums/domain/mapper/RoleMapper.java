package com.zhengcheng.ums.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengcheng.ums.domain.entity.Role;

/**
 * 角色表(Role)表数据库访问层
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色编码列表
     * @param loginId 用户ID
     * @return 角色编码列表
     */
    List<String> getRoleList(@Param("loginId") Object loginId);
}
