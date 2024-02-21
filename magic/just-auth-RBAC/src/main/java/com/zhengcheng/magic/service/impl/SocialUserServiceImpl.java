package com.zhengcheng.magic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.magic.domain.entity.SocialUser;
import com.zhengcheng.magic.domain.mapper.SocialUserMapper;
import com.zhengcheng.magic.service.ISocialUserService;
import org.springframework.stereotype.Service;

/**
 * 社会化用户表(SocialUser)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 16:57:02
 */
@Service
public class SocialUserServiceImpl extends ServiceImpl<SocialUserMapper, SocialUser> implements ISocialUserService {

}
