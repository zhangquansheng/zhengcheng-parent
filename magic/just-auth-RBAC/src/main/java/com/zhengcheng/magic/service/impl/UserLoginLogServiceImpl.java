package com.zhengcheng.magic.service.impl;

import com.zhengcheng.magic.domain.entity.UserLoginLog;
import com.zhengcheng.magic.domain.mapper.UserLoginLogMapper;
import com.zhengcheng.magic.service.IUserLoginLogService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 登录日志表(UserLoginLog)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:49:27
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
    implements IUserLoginLogService {

}