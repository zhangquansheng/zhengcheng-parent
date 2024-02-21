package com.zhengcheng.magic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.magic.domain.entity.UserAuth;
import com.zhengcheng.magic.domain.mapper.UserAuthMapper;
import com.zhengcheng.magic.service.IUserAuthService;
import org.springframework.stereotype.Service;

/**
 * 用户授权表(UserAuth)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:10
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService {

}
