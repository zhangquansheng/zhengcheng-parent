package com.zhengcheng.magic.controller.facade.internal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zhengcheng.magic.controller.command.AuthorityCommand;
import com.zhengcheng.magic.controller.facade.IAuthorityFacade;
import com.zhengcheng.magic.controller.facade.internal.assembler.AuthorityAssembler;
import com.zhengcheng.magic.controller.facade.internal.dto.AuthorityDTO;
import com.zhengcheng.magic.controller.facade.internal.dto.TreeNodeDTO;
import com.zhengcheng.magic.domain.entity.Authority;
import com.zhengcheng.magic.service.IAuthorityService;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 权限表(Authority)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
@Service
public class AuthorityFacadeImpl implements IAuthorityFacade {

    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private AuthorityAssembler authorityAssembler;

    @Override
    public AuthorityDTO findById(Long id) {
        return authorityAssembler.toDTO(authorityService.getById(id));
    }

    @Override
    public Long add(AuthorityCommand authorityCommand) {
        Authority authority = authorityAssembler.toEntity(authorityCommand);
        authorityService.save(authority);
        return authority.getId();
    }

    @Override
    public Long update(AuthorityCommand authorityCommand) {
        Authority authority = authorityAssembler.toEntity(authorityCommand);
        authorityService.update(new LambdaUpdateWrapper<Authority>().set(Authority::getName, authorityCommand.getName())
            .set(Authority::getRoute, authorityCommand.getRoute()).set(Authority::getIcon, authorityCommand.getIcon())
            .set(Authority::getUrl, authorityCommand.getUrl()).set(Authority::getRemark, authorityCommand.getRemark())
            .set(Authority::getType, authorityCommand.getType()).set(Authority::getSort, authorityCommand.getSort())
            .eq(Authority::getId, authorityCommand.getId()));
        return authority.getId();
    }

    @Override
    public List<TreeNodeDTO> findTree() {
        List<Authority> authorities =
            authorityService.list(new LambdaQueryWrapper<Authority>().gt(Authority::getId, 0));
        return this.buildTreeNodeList(authorities, 0L);
    }

    private List<TreeNodeDTO> buildTreeNodeList(List<Authority> authorities, Long pId) {
        List<TreeNodeDTO> treeNodeDTOList = new ArrayList<>();
        for (Authority authority : authorities) {
            if (ObjectUtil.notEqual(authority.getPid(), pId)) {
                continue;
            }

            TreeNodeDTO treeNodeDTO = new TreeNodeDTO();
            treeNodeDTO.setId(authority.getId());
            treeNodeDTO.setLabel(authority.getName());
            List<TreeNodeDTO> children = buildTreeNodeList(authorities, authority.getId());
            if (CollectionUtil.isNotEmpty(children)) {
                treeNodeDTO.setChildren(children);
            }
            treeNodeDTOList.add(treeNodeDTO);
        }
        return treeNodeDTOList;
    }

}
