package com.zhengcheng.magic.controller.facade.internal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.common.domain.PageCommand;
import com.zhengcheng.common.domain.PageInfo;
import com.zhengcheng.magic.controller.command.UserCommand;
import com.zhengcheng.magic.controller.command.UserRoleCommand;
import com.zhengcheng.magic.controller.facade.IUserFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.UserDTO;
import com.zhengcheng.magic.domain.entity.User;
import com.zhengcheng.magic.domain.entity.UserRole;
import com.zhengcheng.magic.service.IRoleAuthorityService;
import com.zhengcheng.magic.service.IUserRoleService;
import com.zhengcheng.magic.service.IUserService;
import com.zhengcheng.mybatis.plus.utils.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;

/**
 * 用户(User)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Service
public class UserFacadeImpl implements IUserFacade {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleAuthorityService roleAuthorityService;

    @Override
    public UserDTO findCurrent(Long id) {
        UserDTO userDTO = this.findById(id);
        userDTO.setRoleCodes(userRoleService.getRoleCodeList(id));
        userDTO.setAuthorityCodes(roleAuthorityService.getAuthorityCodeList(id));
        return userDTO;
    }

    @Override
    public UserDTO findById(Long id) {
        return BeanUtil.copyProperties(userService.getById(id), UserDTO.class);
    }

    @Override
    public Long add(UserCommand userCommand) {
        User user = BeanUtil.copyProperties(userCommand, User.class);
        userService.save(user);
        return user.getId();
    }

    @Override
    public PageInfo<UserDTO> page(PageCommand pageCommand) {
        IPage<User> page = userService.page(PageUtil.getPage(pageCommand));

        PageInfo<UserDTO> pageInfo = PageInfo.empty(pageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(BeanUtil.copyToList(page.getRecords(), UserDTO.class));
        return pageInfo;
    }

    @Override
    public void addUserRole(UserRoleCommand userRoleCommand) {
        List<UserRole> userRoles = new ArrayList<>();
        userRoleCommand.getRoleIds().forEach(
                roleId -> userRoles.add(UserRole.builder().roleId(roleId).userId(userRoleCommand.getUserId()).build()));
        userRoleService.save(userRoles);
    }
}
