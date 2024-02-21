package com.zhengcheng.ums.controller.facade.internal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhengcheng.ums.controller.facade.LogRecordFacade;
import com.zhengcheng.ums.controller.facade.internal.assembler.LogRecordAssembler;
import com.zhengcheng.ums.domain.entity.LogRecord;
import com.zhengcheng.ums.dto.LogRecordDTO;
import com.zhengcheng.ums.service.LogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhengcheng.common.web.PageCommand;
import com.zhengcheng.common.web.PageInfo;
import com.zhengcheng.mybatis.plus.utils.PageUtil;

/**
 * LogRecordFacadeImpl
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 21:32
 */
@Service
public class LogRecordFacadeImpl implements LogRecordFacade {

    @Autowired
    private LogRecordService logRecordService;
    @Autowired
    private LogRecordAssembler logRecordAssembler;

    @Override
    public PageInfo<LogRecordDTO> page(PageCommand pageCommand) {
        IPage<LogRecord> page = logRecordService.page(PageUtil.getPage(pageCommand),
                new LambdaQueryWrapper<LogRecord>().orderByDesc(LogRecord::getCreateTime));

        PageInfo<LogRecordDTO> pageInfo = PageInfo.empty(pageCommand);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(logRecordAssembler.toDTOs(page.getRecords()));
        return pageInfo;
    }
}
