package com.zhengcheng.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.ums.domain.entity.UserLoginLog;
import com.zhengcheng.ums.dto.UserLoginLogDTO;

/**
 * 登录日志表(UserLoginLog)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:49:27
 */
public interface UserLoginLogService extends IService<UserLoginLog> {

    IPage<UserLoginLogDTO> selectPageVo(IPage<?> page);
}
