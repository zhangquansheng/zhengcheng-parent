package com.zhengcheng.magic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.magic.domain.entity.User;

/**
 * 用户(User)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
public interface IUserService extends IService<User> {

    boolean isSamePassword(String password, String encodedPassword);
}