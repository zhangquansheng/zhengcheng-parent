package com.zhengcheng.ums.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengcheng.ums.domain.entity.Authority;

/**
 * 权限表(Authority)表数据库访问层
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:57
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

    List<String> getPermissionList(@Param("loginId") Object loginId);

    List<Authority> getAuthorityList(@Param("userId") Long userId);

    List<Authority> getAuthoritiesByRoleId(@Param("roleId") Long roleId);
}
