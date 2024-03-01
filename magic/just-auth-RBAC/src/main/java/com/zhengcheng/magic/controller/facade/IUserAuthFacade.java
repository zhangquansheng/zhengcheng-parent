package com.zhengcheng.magic.controller.facade;

import com.zhengcheng.magic.controller.facade.internal.dto.UserAuthDTO;
import com.zhengcheng.magic.controller.command.UserAuthCommand;
import com.zhengcheng.common.domain.PageQuery;
import com.zhengcheng.common.domain.PageResult;

/**
 * 用户授权表(UserAuth)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:10
 */
public interface IUserAuthFacade {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return UserAuthDTO
     */
    UserAuthDTO findById(Long id);

    /**
     * 添加单条数据
     *
     * @param userAuthCommand
     *            数据查询对象
     */
    Long add(UserAuthCommand userAuthCommand);

    /**
     * 更新单条数据
     *
     * @param userAuthCommand
     *            数据查询对象
     */
    Long update(UserAuthCommand userAuthCommand);

    /**
     * 分页查询
     *
     * @param pageQuery
     *            分页参数
     * @return 数据查询对象
     */
    PageResult<UserAuthDTO> page(PageQuery pageQuery);
}
