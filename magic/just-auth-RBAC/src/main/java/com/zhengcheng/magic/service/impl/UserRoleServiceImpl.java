package com.zhengcheng.magic.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.magic.domain.entity.Role;
import com.zhengcheng.magic.domain.entity.UserRole;
import com.zhengcheng.magic.domain.mapper.UserRoleMapper;
import com.zhengcheng.magic.service.IUserRoleService;

/**
 * 用户角色表(UserRole)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:58
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<String> getRoleCodeList(Long userId) {
        return userRoleMapper.getRoleList(userId).stream().map(Role::getCode).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void save(List<UserRole> userRoles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return;
        }

        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userRoles.get(0).getUserId()));
        saveBatch(userRoles);
    }
}
