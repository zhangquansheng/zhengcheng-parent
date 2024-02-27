package com.zhengcheng.magic.controller.facade.internal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.magic.controller.command.UserRoleCommand;
import com.zhengcheng.magic.controller.facade.IUserRoleFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.UserRoleDTO;
import com.zhengcheng.magic.domain.entity.UserRole;
import com.zhengcheng.magic.service.IUserRoleService;
import com.zhengcheng.mybatis.plus.utils.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.bean.BeanUtil;

/**
 * 用户角色表(UserRole)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-13 14:26:58
 */
@Service
public class UserRoleFacadeImpl implements IUserRoleFacade {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public UserRoleDTO findById(Long id) {
        return BeanUtil.copyProperties(userRoleService.getById(id), UserRoleDTO.class);
    }

    @Override
    public Long add(UserRoleCommand userRoleCommand) {
        UserRole userRole = BeanUtil.copyProperties(userRoleCommand, UserRole.class);
        userRoleService.save(userRole);
        return userRole.getId();
    }

    @Override
    public Long update(UserRoleCommand userRoleCommand) {
        UserRole userRole = BeanUtil.copyProperties(userRoleCommand, UserRole.class);
        userRoleService.updateById(userRole);
        return userRole.getId();
    }

    @Override
    public PageInfo<UserRoleDTO> page(PageCommand pageCommand) {
        IPage<UserRole> page = userRoleService.page(PageUtil.getPage(pageCommand));

        PageInfo<UserRoleDTO> pageInfo = PageInfo.empty(pageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(BeanUtil.copyToList(page.getRecords(), UserRoleDTO.class));
        return pageInfo;
    }
}
