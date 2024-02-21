package com.zhengcheng.ums.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhengcheng.ums.domain.entity.LogRecord;
import com.zhengcheng.ums.domain.mapper.LogRecordMapper;
import com.zhengcheng.ums.service.LogRecordService;

/**
 * LogRecordServiceImpl
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 19:18
 */
@Service
public class LogRecordServiceImpl extends ServiceImpl<LogRecordMapper, LogRecord> implements LogRecordService {
}
