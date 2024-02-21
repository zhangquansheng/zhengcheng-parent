package com.zhengcheng.magic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.magic.domain.entity.User;
import com.zhengcheng.magic.domain.mapper.UserMapper;
import com.zhengcheng.magic.service.IUserService;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * 用户(User)表服务实现类
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${user.no.random.length}")
    private int userNoRandomLength;
    @Value("${user.password.md5.sign}")
    private String userPasswordMd5Sign;

    @Transactional
    @Override
    public boolean save(User user) {
        user.setUserNo(RandomUtil.randomString(userNoRandomLength));
        user.setPassword(bCryptPasswordEncoder.encode(md5(user.getPassword())));
        user.setLastLogin(LocalDateTimeUtil.now());
        return userMapper.insert(user) > 0;
    }

    private String md5(String password) {
        return SecureUtil.md5(StrUtil.format("{}{}", password, userPasswordMd5Sign));
    }

    @Override
    public boolean isSamePassword(String password, String encodedPassword) {
        return bCryptPasswordEncoder.matches(md5(password), encodedPassword);
    }
}