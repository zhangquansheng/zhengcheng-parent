package com.zhengcheng.ums.controller.admin.monitor;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.ums.domain.entity.SysOperLogEntity;
import com.zhengcheng.ums.service.SysOperLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;

/**
 * 操作日志记录
 */
@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController {

    @Autowired
    private SysOperLogService operLogService;

    @SaCheckPermission("monitor:operlog:list")
    @GetMapping(value = "/list", name = "操作日志-分页")
    public Result list(SysOperLogEntity operLog) {
        PageResult<SysOperLogEntity> page = operLogService.selectOperLogPage(operLog);
        return Result.ok().put(page);
    }

    @SaCheckPermission("monitor:operlog:remove")
    @DeleteMapping(value = "/{operIds}", name = "操作日志-删除")
    public Result remove(@PathVariable Long[] operIds) {
        return Result.ok(operLogService.deleteOperLogByIds(operIds));
    }

    @SaCheckPermission("monitor:operlog:remove")
    @DeleteMapping(value = "/clean", name = "操作日志-清空")
    public Result clean() {
        operLogService.cleanOperLog();
        return Result.ok();
    }
}
