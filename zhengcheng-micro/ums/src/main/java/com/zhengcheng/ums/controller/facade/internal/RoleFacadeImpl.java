package com.zhengcheng.ums.controller.facade.internal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.mybatis.plus.utils.PageUtil;
import com.zhengcheng.ums.common.constants.LogRecordType;
import com.zhengcheng.ums.controller.facade.RoleFacade;
import com.zhengcheng.ums.controller.facade.internal.assembler.RoleAssembler;
import com.zhengcheng.ums.domain.entity.Role;
import com.zhengcheng.ums.domain.entity.RoleAuthority;
import com.zhengcheng.ums.dto.RoleDTO;
import com.zhengcheng.ums.dto.command.EnableCommand;
import com.zhengcheng.ums.dto.command.RoleAuthorityCommand;
import com.zhengcheng.ums.dto.command.RoleCommand;
import com.zhengcheng.ums.dto.command.RolePageCommand;
import com.zhengcheng.ums.service.RoleAuthorityService;
import com.zhengcheng.ums.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.util.StrUtil;

/**
 * 角色表(Role)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
@Service
public class RoleFacadeImpl implements RoleFacade {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    @Autowired
    private RoleAssembler roleAssembler;

    @Override
    public RoleDTO findById(Long id) {
        return roleAssembler.toDTO(roleService.getById(id));
    }

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roles = roleService.list(
                new LambdaQueryWrapper<Role>().eq(Role::getEnable, Boolean.TRUE).orderByDesc(Role::getCreateTime));
        return roleAssembler.toDTOs(roles);
    }

    @LogRecord(success = "删除角色,结果:{{#_ret}}", type = LogRecordType.ROLE, bizNo = "{{#id}}")
    @Override
    public boolean removeById(Long id) {
        Role role = roleService.getById(id);
        if (Objects.nonNull(role) && Boolean.TRUE.equals(role.getSystem())) {
            throw new BizException("系统默认角色，不允许删除！");
        }
        return roleService.removeById(id);
    }

    @Override
    public boolean batchRemove(List<Long> ids) {
        List<Role> roles = roleService.listByIds(ids);
        for (Role role : roles) {
            if (Objects.nonNull(role) && Boolean.TRUE.equals(role.getSystem())) {
                throw new BizException("存在系统默认角色，不允许批量删除！");
            }
        }
        return roleService.removeBatchByIds(ids);
    }

    @LogRecord(success = "{{#enableCommand.enable ? '启用' : '禁用'}}了角色,更新结果:{{#_ret}}", type = LogRecordType.ROLE, bizNo = "{{#enableCommand.id}}")
    @Override
    public boolean enable(EnableCommand enableCommand) {
        Role role = roleService.getById(enableCommand.getId());
        if (Objects.nonNull(role) && Boolean.TRUE.equals(role.getSystem())
                && Boolean.FALSE.equals(enableCommand.isEnable())) {
            throw new BizException("系统默认角色，不允许禁用！");
        }

        return roleService.update(new LambdaUpdateWrapper<Role>().set(Role::getEnable, enableCommand.isEnable())
                .eq(Role::getId, enableCommand.getId()));
    }

    @Override
    public Long add(RoleCommand roleCommand) {
        Role role = roleAssembler.toEntity(roleCommand);
        role.setSystem(Boolean.FALSE);
        role.setEnable(Boolean.TRUE);
        roleService.save(role);
        return role.getId();
    }

    @Override
    public Long update(RoleCommand roleCommand) {
        roleService.update(new LambdaUpdateWrapper<Role>().set(Role::getName, roleCommand.getName())
                .set(Role::getDescription, roleCommand.getDescription()).eq(Role::getId, roleCommand.getId()));
        return roleCommand.getId();
    }

    @LogRecord(success = "分页查询", type = LogRecordType.ROLE, bizNo = "角色列表")
    @Override
    public PageInfo<RoleDTO> page(RolePageCommand rolePageCommand) {
        IPage<Role> page = roleService.page(PageUtil.getPage(rolePageCommand),
                new LambdaQueryWrapper<Role>()
                        .eq(StrUtil.isNotBlank(rolePageCommand.getName()), Role::getName, rolePageCommand.getName())
                        .orderByDesc(Role::getCreateTime));

        PageInfo<RoleDTO> pageInfo = PageInfo.empty(rolePageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(roleAssembler.toDTOs(page.getRecords()));
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleAuthority(RoleAuthorityCommand roleAuthorityCommand) {
        roleAuthorityService.remove(new LambdaUpdateWrapper<RoleAuthority>().eq(RoleAuthority::getRoleId,
                roleAuthorityCommand.getRoleId()));
        if (StrUtil.isEmpty(roleAuthorityCommand.getAuthorityIds())) {
            return true;
        }

        List<String> authorityIds = Arrays.asList(roleAuthorityCommand.getAuthorityIds().split(","));
        List<RoleAuthority> roleAuthorities = new ArrayList<>();
        authorityIds.forEach(authorityId -> {
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRoleId(roleAuthorityCommand.getRoleId());
            roleAuthority.setAuthorityId(Long.valueOf(authorityId));
            roleAuthorities.add(roleAuthority);
        });
        return roleAuthorityService.saveBatch(roleAuthorities);
    }
}
