package com.zhengcheng.ums.controller.facade.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zhengcheng.ums.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mzt.logapi.starter.annotation.LogRecord;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.mybatis.plus.utils.PageUtil;
import com.zhengcheng.ums.common.constants.LogRecordType;
import com.zhengcheng.ums.common.exception.UmsError;
import com.zhengcheng.ums.controller.facade.UserFacade;
import com.zhengcheng.ums.controller.facade.internal.assembler.UserAssembler;
import com.zhengcheng.ums.domain.entity.Authority;
import com.zhengcheng.ums.domain.entity.User;
import com.zhengcheng.ums.domain.enums.AuthorityTypeEnum;
import com.zhengcheng.ums.dto.MenuDTO;
import com.zhengcheng.ums.dto.command.EnableCommand;
import com.zhengcheng.ums.dto.command.UserCommand;
import com.zhengcheng.ums.dto.command.UserPageCommand;
import com.zhengcheng.ums.service.AuthorityService;
import com.zhengcheng.ums.service.RoleAuthorityService;
import com.zhengcheng.ums.service.UserRoleService;
import com.zhengcheng.ums.service.UserService;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 用户(User)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 16:31:49
 */
@Service
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService          userService;
    @Autowired
    private UserRoleService      userRoleService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    @Autowired
    private AuthorityService     authorityService;
    @Autowired
    private UserAssembler        userAssembler;

    @Override
    public UserDTO findByByToken(String tokenValue) {
        Object loginId = StpUtil.getLoginIdByToken(tokenValue);
        if (Objects.isNull(loginId)) {
            throw new BizException(UmsError.NOT_LOGIN_EXCEPTION.getCode(), UmsError.NOT_LOGIN_EXCEPTION.getMessage());
        }

        Long id = Long.valueOf(String.valueOf(loginId));
        UserDTO userDTO = this.findById(id);
        userDTO.setRoleCodes(userRoleService.getRoleCodeList(id));
        userDTO.setAuthorityCodes(roleAuthorityService.getAuthorityCodeList(id));
        return userDTO;
    }

    @Override
    public UserDTO findById(Long id) {
        return userAssembler.toDTO(userService.getById(id));
    }

    @Override
    public UserDTO findByUsername(String username) {
        return userAssembler.toDTO(userService.findByUsername(username));
    }

    @Override
    public Long add(UserCommand userCommand) {
        return userService.add(userCommand);
    }

    @Override
    public void update(UserCommand userCommand) {
        userService.update(userCommand);
    }

    @Override
    public void enable(EnableCommand enableCommand) {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(enableCommand.getId());
        userCommand.setEnable(enableCommand.isEnable());
        userCommand.setSource(UserCommand.SOURCE_ADMIN);
        userService.update(userCommand);
    }

    @LogRecord(success = "分页查询", type = LogRecordType.USER, bizNo = "用户列表")
    @Override
    public PageInfo<UserDTO> page(UserPageCommand userPageCommand) {
        IPage<User> page = userService.page(PageUtil.getPage(userPageCommand), new LambdaQueryWrapper<User>()
                .eq(StrUtil.isNotBlank(userPageCommand.getUsername()), User::getUsername, userPageCommand.getUsername())
                .eq(StrUtil.isNotBlank(userPageCommand.getName()), User::getName, userPageCommand.getName())
                .eq(StrUtil.isNotBlank(userPageCommand.getMobile()), User::getMobile, userPageCommand.getMobile())
                .orderByDesc(User::getCreateTime));

        PageInfo<UserDTO> pageInfo = PageInfo.empty(userPageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(userAssembler.toDTOs(page.getRecords()));
        return pageInfo;
    }

    @Override
    public List<MenuDTO> menu(Long userId) {
        List<Authority> authorities = authorityService.getAuthorityList(userId);
        // 查询 pid=0 的目录
        List<Authority> catalogues = authorities.stream()
                .filter(s -> (s.getPid() == 0 && AuthorityTypeEnum.CATALOGUE.equals(s.getType())))
                .collect(Collectors.toList());
        List<MenuDTO> menuDTOs = new ArrayList<>();
        catalogues.forEach(catalogue -> {
            MenuDTO menuDTO = this.toMenuDTO(catalogue);
            // 获取目录下的菜单
            List<Authority> menus = authorities.stream()
                    .filter(s -> (s.getPid().equals(catalogue.getId()) && AuthorityTypeEnum.MENU.equals(s.getType())))
                    .collect(Collectors.toList());
            List<MenuDTO> children = new ArrayList<>();
            menus.forEach(menu -> children.add(this.toMenuDTO(menu)));
            menuDTO.setChildren(children);
            menuDTOs.add(menuDTO);
        });
        return menuDTOs;
    }

    @Override
    public boolean usernameExists(String username) {
        User user = userService.findByUsername(username);
        return Objects.nonNull(user);
    }

    private MenuDTO toMenuDTO(Authority authority) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(authority.getId());
        menuDTO.setTitle(authority.getName());
        menuDTO.setIcon(authority.getIcon());
        menuDTO.setType(authority.getType().getValue());
        menuDTO.setOpenType(AuthorityTypeEnum.MENU.equals(authority.getType()) ? "_iframe" : "");
        menuDTO.setHref(authority.getUrl());
        return menuDTO;
    }
}
