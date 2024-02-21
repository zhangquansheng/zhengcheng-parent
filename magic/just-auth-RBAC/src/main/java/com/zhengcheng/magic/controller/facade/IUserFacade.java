package com.zhengcheng.magic.controller.facade;

import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.magic.controller.command.UserCommand;
import com.zhengcheng.magic.controller.command.UserRoleCommand;
import com.zhengcheng.magic.controller.facade.internal.dto.UserDTO;

/**
 * 用户(User)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
public interface IUserFacade {

    /**
     * 查询当前登录的用户，返回角色，权限
     * 
     * @param id
     *            用户ID
     * @return UserDTO
     */
    UserDTO findCurrent(Long id);

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return UserDTO
     */
    UserDTO findById(Long id);

    /**
     * 添加单条数据
     *
     * @param userCommand
     *            数据查询对象
     */
    Long add(UserCommand userCommand);

    /**
     * 分页查询
     *
     * @param pageCommand
     *            分页参数
     * @return 数据查询对象
     */
    PageInfo<UserDTO> page(PageCommand pageCommand);

    /**
     * 添加用户角色
     * 
     * @param userRoleCommand
     *            UserRoleCommand
     */
    void addUserRole(UserRoleCommand userRoleCommand);
}
