package com.zhengcheng.ums.controller.facade.internal;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import com.zhengcheng.ums.service.LogRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * DbLogRecordFacadeImpl
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 19:19
 */
@Slf4j
@Service
public class DbLogRecordFacadeImpl implements ILogRecordService {

    @Autowired
    private LogRecordService logRecordService;

    @Override
    public void record(LogRecord logRecord) {
        log.info("【logRecord】log={}", logRecord);

        com.zhengcheng.ums.domain.entity.LogRecord record = new com.zhengcheng.ums.domain.entity.LogRecord();
        BeanUtils.copyProperties(logRecord, record);
        logRecordService.save(record);
    }

    @Override
    public List<LogRecord> queryLog(String bizNo, String type) {
        return Lists.newArrayList();
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo, String type, String subType) {
        return Lists.newArrayList();
    }
}
