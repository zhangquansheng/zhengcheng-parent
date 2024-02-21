package com.zhengcheng.ums.controller.facade;

import java.util.List;

import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.ums.dto.UserDTO;
import com.zhengcheng.ums.dto.command.EnableCommand;
import com.zhengcheng.ums.dto.command.UserCommand;
import com.zhengcheng.ums.dto.command.UserPageCommand;
import com.zhengcheng.ums.dto.MenuDTO;

/**
 * 用户(User)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
public interface UserFacade {

    /**
     * 通过token获取用户消息
     * @param tokenValue 令牌
     * @return UserDTO
     */
    UserDTO findByByToken(String tokenValue);

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return UserDTO
     */
    UserDTO findById(Long id);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return UserDTO
     */
    UserDTO findByUsername(String username);

    /**
     * 添加单条数据
     *
     * @param userCommand
     *            UserCommand
     */
    Long add(UserCommand userCommand);

    /**
     * 更新单条数据
     *
     * @param userCommand
     *            UserCommand
     */
    void update(UserCommand userCommand);

    /**
     * 禁用/启用
     */
    void enable(EnableCommand enableCommand);

    /**
     * 分页查询
     *
     * @param userPageCommand
     *            分页参数
     * @return 数据查询对象
     */
    PageInfo<UserDTO> page(UserPageCommand userPageCommand);

    /**
     * 查询用户的菜单
     * @param userId 用户ID
     * @return 菜单
     */
    List<MenuDTO> menu(Long userId);

    /**
     * 用户名是否存在
     * @param username 用户名
     * @return 是/否
     */
    boolean usernameExists(String username);
}
