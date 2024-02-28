package com.zhengcheng.magic.controller.facade;

import com.zhengcheng.common.domain.PageCommand;
import com.zhengcheng.common.domain.PageInfo;
import com.zhengcheng.magic.controller.facade.internal.dto.UserLoginLogDTO;

/**
 * 登录日志表(UserLoginLog)表Facade接口
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:51:46
 */
public interface IUserLoginLogFacade {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     *            主键
     * @return UserLoginLogDTO
     */
    UserLoginLogDTO findById(Long id);

    /**
     * 分页查询
     *
     * @param pageCommand
     *            分页参数
     * @return 数据查询对象
     */
    PageInfo<UserLoginLogDTO> page(PageCommand pageCommand);
}
