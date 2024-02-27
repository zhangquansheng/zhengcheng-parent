package com.zhengcheng.magic.controller.facade.internal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.magic.controller.facade.IUserLoginLogFacade;
import com.zhengcheng.magic.controller.facade.internal.dto.UserLoginLogDTO;
import com.zhengcheng.magic.domain.entity.UserLoginLog;
import com.zhengcheng.magic.service.IUserLoginLogService;
import com.zhengcheng.mybatis.plus.utils.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.bean.BeanUtil;

/**
 * 登录日志表(UserLoginLog)外观模式，接口实现
 *
 * @author quansheng1.zhang
 * @since 2021-07-15 19:51:46
 */
@Service
public class UserLoginLogFacadeImpl implements IUserLoginLogFacade {

    @Autowired
    private IUserLoginLogService userLoginLogService;

    @Override
    public UserLoginLogDTO findById(Long id) {
        return BeanUtil.copyProperties(userLoginLogService.getById(id), UserLoginLogDTO.class);
    }

    @Override
    public PageInfo<UserLoginLogDTO> page(PageCommand pageCommand) {
        IPage<UserLoginLog> page = userLoginLogService.page(PageUtil.getPage(pageCommand),
                new LambdaQueryWrapper<UserLoginLog>().orderByDesc(UserLoginLog::getCreateTime));

        PageInfo<UserLoginLogDTO> pageInfo = PageInfo.empty(pageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(BeanUtil.copyToList(page.getRecords(), UserLoginLogDTO.class));
        return pageInfo;
    }
}
