package com.zhengcheng.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhengcheng.ums.domain.entity.User;
import com.zhengcheng.ums.dto.command.UserCommand;

/**
 * 用户(User)表服务接口
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return UserDTO
     */
    User findByUsername(String username);

    /**
     * 添加用户
     * @param userCommand UserCommand
     * @return 用户ID
     */
    Long add(UserCommand userCommand);

    /**
     * 更新用户
     * @param userCommand UserCommand
     */
    void update(UserCommand userCommand);

    /**
     * 是否匹配相等
     * 
     * @param password 密码明文
     * @param encryptedPassword 密码密文
     * @return 密文
     */
    boolean matches(String password, String encryptedPassword);
}
