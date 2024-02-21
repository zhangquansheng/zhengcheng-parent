package com.zhengcheng.ums.controller.facade.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.ums.common.constants.LogRecordType;
import com.zhengcheng.ums.controller.facade.AuthorityFacade;
import com.zhengcheng.ums.controller.facade.internal.assembler.AuthorityAssembler;
import com.zhengcheng.ums.domain.entity.Authority;
import com.zhengcheng.ums.dto.AuthorityDTO;
import com.zhengcheng.ums.dto.command.AuthorityCommand;
import com.zhengcheng.ums.dto.command.EnableCommand;
import com.zhengcheng.ums.service.AuthorityService;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 权限表(Authority)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@Service
public class AuthorityFacadeImpl implements AuthorityFacade {

    @Autowired
    private AuthorityService   authorityService;
    @Autowired
    private AuthorityAssembler authorityAssembler;

    @Override
    public List<AuthorityDTO> findAll() {
        return findByPid(null);
    }

    @LogRecord(success = "查询", type = LogRecordType.AUTHORITY, bizNo = "权限列表")
    @Override
    public List<AuthorityDTO> findByPid(Long pid) {
        return authorityAssembler.toDTOs(authorityService
                .list(new LambdaQueryWrapper<Authority>().eq(Objects.nonNull(pid), Authority::getPid, pid)
                        .orderBy(Boolean.TRUE, Boolean.TRUE, Authority::getSort)));
    }

    @Override
    public AuthorityDTO findById(Long id) {
        return authorityAssembler.toDTO(authorityService.getById(id));
    }

    @LogRecord(success = "新增了权限：{{#authorityCommand.name}}", type = LogRecordType.AUTHORITY, subType = LogRecordType.ADD_SUB_TYPE, bizNo = "{{#authorityCommand.code}}")
    @Override
    public Long save(AuthorityCommand authorityCommand) {
        // 验证权限编码唯一
        if (StrUtil.isNotBlank(authorityCommand.getCode())) {
            Authority sameCode = authorityService
                    .getOne(new LambdaQueryWrapper<Authority>().eq(Authority::getCode, authorityCommand.getCode()));
            if (Objects.nonNull(sameCode)) {
                throw new BizException(StrUtil.format("已存在相同的权限编码,权限名称为:{}", sameCode.getName()));
            }
        }
        Authority authority = authorityAssembler.toEntity(authorityCommand);
        authorityService.save(authority);
        return authority.getId();
    }

    @LogRecord(success = "更新了权限{_DIFF{#authorityCommand}}", type = LogRecordType.AUTHORITY, subType = LogRecordType.UPDATE_SUB_TYPE, bizNo = "{{#authorityCommand.id}}")
    @Override
    public boolean update(AuthorityCommand authorityCommand) {
        return authorityService.update(new LambdaUpdateWrapper<Authority>()
                .set(Authority::getName, authorityCommand.getName())
                .set(Authority::getRoute, authorityCommand.getRoute())
                .set(Authority::getIcon, authorityCommand.getIcon()).set(Authority::getUrl, authorityCommand.getUrl())
                .set(Authority::getRemark, authorityCommand.getRemark())
                .set(Authority::getType, authorityCommand.getType()).set(Authority::getSort, authorityCommand.getSort())
                .eq(Authority::getId, authorityCommand.getId()));
    }

    @LogRecord(success = "{{#enableCommand.enable ? '启用' : '禁用'}}了权限,更新结果:{{#_ret}}", type = LogRecordType.ROLE, bizNo = "{{#enableCommand.id}}")
    @Override
    public boolean enable(EnableCommand enableCommand) {
        return authorityService.update(new LambdaUpdateWrapper<Authority>()
                .set(Authority::getEnable, enableCommand.isEnable()).eq(Authority::getId, enableCommand.getId()));
    }

    @LogRecord(success = "删除权限", type = LogRecordType.AUTHORITY, subType = LogRecordType.DELETE_SUB_TYPE, bizNo = "{{#id}}")
    @Override
    public boolean deleteById(Long id) {
        List<Authority> authorityList = authorityService.list(
                new LambdaQueryWrapper<Authority>().eq(Authority::getPid, id).eq(Authority::getEnable, Boolean.TRUE)
                        .orderBy(Boolean.TRUE, Boolean.TRUE, Authority::getSort));
        if (CollectionUtil.isNotEmpty(authorityList)) {
            throw new BizException("此权限下存在子权限，无法删除！");
        }
        return authorityService.removeById(id);
    }

    @Override
    public List<AuthorityDTO> findByRoleId(Long roleId) {
        List<AuthorityDTO> authorityList = this.findAll();

        List<Long> roleAuthorityIds = new ArrayList<>();
        if (Objects.nonNull(roleId)) {
            List<Authority> roleAuthorityList = authorityService.getAuthoritiesByRoleId(roleId);
            roleAuthorityIds = roleAuthorityList.stream().map(Authority::getId).collect(Collectors.toList());
        }
        for (AuthorityDTO authorityDTO : authorityList) {
            authorityDTO.setCheckArr(roleAuthorityIds.contains(authorityDTO.getId()) ? "1" : "0");
        }
        return authorityList;
    }

}
