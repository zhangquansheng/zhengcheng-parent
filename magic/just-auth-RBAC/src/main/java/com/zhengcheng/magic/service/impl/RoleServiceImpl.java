package com.zhengcheng.magic.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.magic.domain.entity.Role;
import com.zhengcheng.magic.domain.mapper.RoleMapper;
import com.zhengcheng.magic.service.IRoleService;

import cn.hutool.core.util.StrUtil;

/**
 * 角色表(Role)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(Role role) {
        Role existingRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, role.getCode()));
        if (Objects.isNull(existingRole)) {
            return roleMapper.insert(role) > 0;
        }
        throw new BizException(StrUtil.format("角色编码【{}】已存在！", role.getCode()));
    }
}
