package com.zhengcheng.ums.controller.facade;

import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.ums.dto.UserLoginLogDTO;

/**
 * UserLoginLogFacade
 *
 * @author quansheng1.zhang
 * @since 2022/5/1 22:00
 */
public interface UserLoginLogFacade {

    /**
     * 分页查询
     *
     * @param pageCommand 分页参数
     * @return 数据查询对象
     */
    PageInfo<UserLoginLogDTO> page(PageCommand pageCommand);

}
