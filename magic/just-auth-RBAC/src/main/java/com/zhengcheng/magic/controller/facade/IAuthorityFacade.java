package com.zhengcheng.magic.controller.facade;

import java.util.List;

import com.zhengcheng.magic.controller.command.AuthorityCommand;
import com.zhengcheng.magic.controller.facade.internal.dto.AuthorityDTO;
import com.zhengcheng.magic.controller.facade.internal.dto.TreeNodeDTO;

/**
 * 权限表(Authority)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:46:58
 */
public interface IAuthorityFacade {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return AuthorityDTO
     */
    AuthorityDTO findById(Long id);

    /**
     * 添加单条数据
     *
     * @param authorityCommand
     *            数据查询对象
     */
    Long add(AuthorityCommand authorityCommand);

    /**
     * 更新单条数据
     *
     * @param authorityCommand
     *            数据查询对象
     */
    Long update(AuthorityCommand authorityCommand);

    /**
     * 查询权限树
     * 
     * @return 权限树节点列表
     */
    List<TreeNodeDTO> findTree();
}
