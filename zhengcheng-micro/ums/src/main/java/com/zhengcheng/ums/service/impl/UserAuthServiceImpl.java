package com.zhengcheng.ums.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.UserAuth;
import com.zhengcheng.ums.domain.mapper.UserAuthMapper;
import com.zhengcheng.ums.service.UserAuthService;

/**
 * 用户授权表(UserAuth)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:10
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

}
