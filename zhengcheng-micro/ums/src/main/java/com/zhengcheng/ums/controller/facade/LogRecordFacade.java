package com.zhengcheng.ums.controller.facade;

import com.zhengcheng.common.web.PageCommand;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.ums.dto.LogRecordDTO;

/**
 * LogRecordFacade
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 21:31
 */
public interface LogRecordFacade {

    /**
     * 分页查询
     *
     * @param pageCommand
     *            分页参数
     * @return 数据查询对象
     */
    PageInfo<LogRecordDTO> page(PageCommand pageCommand);
}
