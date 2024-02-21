package com.zhengcheng.ums.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.ums.domain.entity.User;
import com.zhengcheng.ums.domain.entity.UserRole;
import com.zhengcheng.ums.domain.mapper.UserMapper;
import com.zhengcheng.ums.domain.mapper.UserRoleMapper;
import com.zhengcheng.ums.dto.command.UserCommand;
import com.zhengcheng.ums.service.UserService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper            userMapper;
    @Autowired
    private UserRoleMapper        userRoleMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 用户编号随机长度
     */
    public final int              USER_NO_RANDOM_LENGTH = 6;

    @Override
    public User findByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            throw new BizException("用户名不能为空");
        }
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long add(UserCommand userCommand) {
        User user = findByUsername(userCommand.getUsername());
        if (Objects.nonNull(user)) {
            throw new BizException("用户名已存在！");
        }

        user = BeanUtil.copyProperties(userCommand, User.class);
        user.setUserNo(RandomUtil.randomString(USER_NO_RANDOM_LENGTH));
        user.setPassword(getEncryptedPassword(userCommand.getPassword()));
        user.setEnable(Boolean.TRUE);
        userMapper.insert(user);

        saveBatchUserRole(user.getId(), userCommand.getRoleIds());

        return user.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserCommand userCommand) {
        userMapper.update(null,
                new LambdaUpdateWrapper<User>()
                        .set(StrUtil.isNotBlank(userCommand.getName()), User::getName, userCommand.getName())
                        .set(StrUtil.isNotBlank(userCommand.getEmail()), User::getEmail, userCommand.getEmail())
                        .set(StrUtil.isNotBlank(userCommand.getMobile()), User::getMobile, userCommand.getMobile())
                        .set(StrUtil.isNotBlank(userCommand.getPassword()), User::getPassword,
                                getEncryptedPassword(userCommand.getPassword()))
                        .set(Objects.nonNull(userCommand.getEnable()), User::getEnable, userCommand.getEnable())
                        .eq(User::getId, userCommand.getId()));

        // 启用/禁用用户，则不需要更新用户的角色
        if (Objects.nonNull(userCommand.getEnable()) && CollectionUtil.isEmpty(userCommand.getRoleIds())) {
            return;
        }

        // 更新用户的角色
        userRoleMapper.delete(new LambdaUpdateWrapper<UserRole>().eq(UserRole::getUserId, userCommand.getId()));
        this.saveBatchUserRole(userCommand.getId(), userCommand.getRoleIds());
    }

    @Override
    public boolean matches(String password, String encryptedPassword) {
        return bCryptPasswordEncoder.matches(SecureUtil.md5(password), encryptedPassword);
    }

    private String getEncryptedPassword(String password) {
        return bCryptPasswordEncoder.encode(SecureUtil.md5(password));
    }

    private void saveBatchUserRole(Long userId, List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }

        List<UserRole> userRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        });

        if (CollectionUtils.isNotEmpty(userRoles)) {
            userRoles.forEach(userRole -> userRoleMapper.insert(userRole));
        }
    }

}
