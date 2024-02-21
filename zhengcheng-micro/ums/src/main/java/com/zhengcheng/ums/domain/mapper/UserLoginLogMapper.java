package com.zhengcheng.ums.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.ums.domain.entity.UserLoginLog;
import com.zhengcheng.ums.dto.UserLoginLogDTO;

/**
 * 登录日志表(UserLoginLog)表数据库访问层
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:49:27
 */
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {

    IPage<UserLoginLogDTO> selectPageVo(IPage<?> page);
}
