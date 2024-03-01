package com.zhengcheng.magic.controller.facade;

import com.zhengcheng.magic.controller.facade.internal.dto.UserRoleDTO;
import com.zhengcheng.magic.controller.command.UserRoleCommand;
import com.zhengcheng.common.domain.PageQuery;
import com.zhengcheng.common.domain.PageResult;

/**
 * 用户角色表(UserRole)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:58
 */
public interface IUserRoleFacade {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return UserRoleDTO
     */
    UserRoleDTO findById(Long id);

    /**
     * 添加单条数据
     *
     * @param userRoleCommand
     *            数据查询对象
     */
    Long add(UserRoleCommand userRoleCommand);

    /**
     * 更新单条数据
     *
     * @param userRoleCommand
     *            数据查询对象
     */
    Long update(UserRoleCommand userRoleCommand);

    /**
     * 分页查询
     *
     * @param pageQuery
     *            分页参数
     * @return 数据查询对象
     */
    PageResult<UserRoleDTO> page(PageQuery pageQuery);
}
