package com.zhengcheng.magic.controller.facade.internal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.common.domain.PageQuery;
import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.magic.controller.command.UserAuthCommand;
import com.zhengcheng.magic.controller.facade.IUserAuthFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.UserAuthDTO;
import com.zhengcheng.magic.domain.entity.UserAuth;
import com.zhengcheng.magic.service.IUserAuthService;
import com.zhengcheng.mybatis.plus.utils.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.bean.BeanUtil;

/**
 * 用户授权表(UserAuth)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-08-14 17:28:10
 */
@Service
public class UserAuthFacadeImpl implements IUserAuthFacade {

    @Autowired
    private IUserAuthService userAuthService;

    @Override
    public UserAuthDTO findById(Long id) {
        return BeanUtil.copyProperties(userAuthService.getById(id), UserAuthDTO.class);
    }

    @Override
    public Long add(UserAuthCommand userAuthCommand) {
        UserAuth userAuth = BeanUtil.copyProperties(userAuthCommand, UserAuth.class);
        userAuthService.save(userAuth);
        return userAuth.getId();
    }

    @Override
    public Long update(UserAuthCommand userAuthCommand) {
        UserAuth userAuth = BeanUtil.copyProperties(userAuthCommand, UserAuth.class);
        userAuthService.updateById(userAuth);
        return userAuth.getId();
    }

    @Override
    public PageResult<UserAuthDTO> page(PageQuery pageQuery) {
        IPage<UserAuth> page = userAuthService.page(PageUtil.getPage(pageQuery));

        PageResult<UserAuthDTO> pageInfo = PageResult.empty(pageQuery);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(BeanUtil.copyToList(page.getRecords(), UserAuthDTO.class));
        return pageInfo;
    }
}
