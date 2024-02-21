package com.zhengcheng.ums.controller;

import com.zhengcheng.common.utils.PageCommand;
import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.common.utils.Result;
import com.zhengcheng.ums.controller.facade.LogRecordFacade;
import com.zhengcheng.ums.dto.LogRecordDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * LogRecordController
 *
 * @author quansheng1.zhang
 * @since 2022/4/30 21:23
 */
@Api(tags = {"日志管理"})
@RestController
@RequestMapping("/admin/log")
public class LogRecordController {

    @Autowired
    private LogRecordFacade logRecordFacade;

    @ApiOperation("分页查询")
    @SaCheckPermission("sys:log:main")
    @PostMapping("/page")
    public Result<PageInfo<LogRecordDTO>> page(@Valid @RequestBody PageCommand pageCommand) {
        return Result.successData(logRecordFacade.page(pageCommand));
    }
}
