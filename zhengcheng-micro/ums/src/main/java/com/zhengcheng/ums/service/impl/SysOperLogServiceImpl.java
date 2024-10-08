package com.zhengcheng.ums.service.impl;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.ums.domain.entity.SysOperLogEntity;
import com.zhengcheng.ums.mapper.SysOperLogMapper;
import com.zhengcheng.ums.service.SysOperLogService;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * 操作日志 服务层处理
 */
@Service
public class SysOperLogServiceImpl implements SysOperLogService {
    @Resource
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLogEntity operLog) {
        operLogMapper.insert(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public PageResult<SysOperLogEntity> selectOperLogPage(SysOperLogEntity operLog) {
        return operLogMapper.selectOperLogPage(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLogEntity selectOperLogById(Long operId) {
        return operLogMapper.selectById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }

    @Override
    public List<SysOperLogEntity> selectOperLogList(SysOperLogEntity operLog) {
        return operLogMapper.selectOperLogList(operLog);
    }
}
