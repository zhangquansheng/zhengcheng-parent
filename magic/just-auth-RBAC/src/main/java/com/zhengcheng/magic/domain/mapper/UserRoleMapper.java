package com.zhengcheng.magic.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengcheng.magic.domain.entity.Role;
import com.zhengcheng.magic.domain.entity.UserRole;

/**
 * 用户角色表(UserRole)表数据库访问层
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:57
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<Role> getRoleList(@Param("userId") Long userId);
}
