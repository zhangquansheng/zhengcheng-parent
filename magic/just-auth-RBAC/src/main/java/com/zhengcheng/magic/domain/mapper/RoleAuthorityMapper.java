package com.zhengcheng.magic.domain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhengcheng.magic.domain.entity.Authority;
import com.zhengcheng.magic.domain.entity.RoleAuthority;

/**
 * 角色权限表(RoleAuthority)表数据库访问层
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:57:02
 */
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {

    /**
     * 根据角色列表获取权限
     *
     * @param roleIds
     *            角色ID列表
     * @return 权限
     */
    List<Authority> getAuthorityList(@Param("roleIds") List<Long> roleIds);
}
