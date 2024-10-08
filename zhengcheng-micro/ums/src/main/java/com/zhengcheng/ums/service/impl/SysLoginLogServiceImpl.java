package com.zhengcheng.ums.service.impl;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.ums.domain.entity.SysLoginLogEntity;
import com.zhengcheng.ums.mapper.SysLoginLogMapper;
import com.zhengcheng.ums.service.SysLoginLogService;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Resource
    private SysLoginLogMapper loginLogMapper;

    /**
     * 查询系统登录日志分页数据
     *
     * @param logininfor 访问日志对象
     * @return 登录记录分页数据
     */
    @Override
    public PageResult<SysLoginLogEntity> selectLogininforPage(SysLoginLogEntity logininfor) {
        return loginLogMapper.selectLogininforPage(logininfor);
    }

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLoginLogEntity logininfor) {
        loginLogMapper.insert(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginLogEntity> selectLogininforList(SysLoginLogEntity logininfor) {
        return loginLogMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return loginLogMapper.deleteBatchIds(Arrays.asList(infoIds));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        loginLogMapper.cleanLogininfor();
    }
}
