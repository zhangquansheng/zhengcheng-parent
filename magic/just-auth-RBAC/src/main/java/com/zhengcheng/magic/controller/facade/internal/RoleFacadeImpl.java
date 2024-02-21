package com.zhengcheng.magic.controller.facade.internal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.magic.controller.command.RoleAuthorityCommand;
import com.zhengcheng.magic.controller.command.RoleCommand;
import com.zhengcheng.magic.controller.facade.IRoleFacade;
import com.zhengcheng.magic.controller.facade.internal.assembler.RoleAssembler;
import com.zhengcheng.magic.controller.facade.internal.dto.RoleDTO;
import com.zhengcheng.magic.domain.entity.Role;
import com.zhengcheng.magic.domain.entity.RoleAuthority;
import com.zhengcheng.magic.service.IRoleAuthorityService;
import com.zhengcheng.magic.service.IRoleService;
import com.zhengcheng.mybatis.plus.utils.PageUtil;

/**
 * 角色表(Role)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@Service
public class RoleFacadeImpl implements IRoleFacade {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private RoleAssembler roleAssembler;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;

    @Override
    public RoleDTO findById(Long id) {
        return roleAssembler.toDTO(roleService.getById(id));
    }

    @Override
    public Long add(RoleCommand roleCommand) {
        Role role = roleAssembler.toEntity(roleCommand);
        roleService.save(role);
        return role.getId();
    }

    @Override
    public Long update(RoleCommand roleCommand) {
        roleService.update(new LambdaUpdateWrapper<Role>().set(Role::getName, roleCommand.getName())
            .set(Role::getDescription, roleCommand.getDescription()).eq(Role::getId, roleCommand.getId()));
        return roleCommand.getId();
    }

    @Override
    public PageInfo<RoleDTO> page(PageCommand pageCommand) {
        IPage<Role> page = roleService.page(PageUtil.getPage(pageCommand),
            new LambdaQueryWrapper<Role>().orderByDesc(Role::getCreateTime));

        PageInfo<RoleDTO> pageInfo = PageInfo.empty(pageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(roleAssembler.toDTOs(page.getRecords()));
        return pageInfo;
    }

    @Override
    public void addRoleAuthority(RoleAuthorityCommand roleAuthorityCommand) {
        List<RoleAuthority> roleAuthorityList = new ArrayList<>();
        roleAuthorityCommand.getAuthorityIds().forEach(authorityId -> roleAuthorityList
            .add(RoleAuthority.builder().authorityId(authorityId).roleId(roleAuthorityCommand.getRoleId()).build()));
        roleAuthorityService.save(roleAuthorityList);
    }
}
