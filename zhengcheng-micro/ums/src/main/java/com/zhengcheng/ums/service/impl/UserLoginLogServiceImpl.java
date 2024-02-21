package com.zhengcheng.ums.service.impl;

import com.zhengcheng.ums.dto.UserLoginLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.UserLoginLog;
import com.zhengcheng.ums.domain.mapper.UserLoginLogMapper;
import com.zhengcheng.ums.service.UserLoginLogService;

/**
 * 登录日志表(UserLoginLog)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:49:27
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
        implements UserLoginLogService {

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Override
    public IPage<UserLoginLogDTO> selectPageVo(IPage<?> page) {
        return userLoginLogMapper.selectPageVo(page);
    }
}
