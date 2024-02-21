package com.zhengcheng.ums.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.Authority;
import com.zhengcheng.ums.domain.entity.Role;
import com.zhengcheng.ums.domain.entity.RoleAuthority;
import com.zhengcheng.ums.domain.mapper.RoleAuthorityMapper;
import com.zhengcheng.ums.domain.mapper.UserRoleMapper;
import com.zhengcheng.ums.service.RoleAuthorityService;

/**
 * 角色权限表(RoleAuthority)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:57:02
 */
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority>
    implements RoleAuthorityService {

    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<String> getAuthorityCodeList(Long userId) {
        List<Role> roleList = userRoleMapper.getRoleList(userId);
        if (CollectionUtils.isEmpty(roleList)) {
            return new ArrayList<>();
        }

        return roleAuthorityMapper.getAuthorityList(roleList.stream().map(Role::getId).collect(Collectors.toList()))
            .stream().map(Authority::getCode).distinct().collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(List<RoleAuthority> roleAuthorities) {
        if (CollectionUtils.isEmpty(roleAuthorities)) {
            return;
        }

        roleAuthorityMapper.delete(
            new LambdaQueryWrapper<RoleAuthority>().eq(RoleAuthority::getRoleId, roleAuthorities.get(0).getRoleId()));
        saveBatch(roleAuthorities);
    }
}
