package com.zhengcheng.magic.controller.facade;

import com.zhengcheng.common.domain.PageCommand;
import com.zhengcheng.common.domain.PageInfo;
import com.zhengcheng.magic.controller.command.RoleAuthorityCommand;
import com.zhengcheng.magic.controller.command.RoleCommand;
import com.zhengcheng.magic.controller.facade.internal.dto.RoleDTO;

/**
 * 角色表(Role)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:19:03
 */
public interface IRoleFacade {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return RoleDTO
     */
    RoleDTO findById(Long id);

    /**
     * 添加单条数据
     *
     * @param roleCommand
     *            数据查询对象
     */
    Long add(RoleCommand roleCommand);

    /**
     * 更新单条数据
     *
     * @param roleCommand
     *            数据查询对象
     */
    Long update(RoleCommand roleCommand);

    /**
     * 分页查询
     *
     * @param pageCommand
     *            分页参数
     * @return 数据查询对象
     */
    PageInfo<RoleDTO> page(PageCommand pageCommand);

    /**
     * 编辑角色权限
     *
     * @param roleAuthorityCommand
     *            角色权限
     */
    void addRoleAuthority(RoleAuthorityCommand roleAuthorityCommand);
}
