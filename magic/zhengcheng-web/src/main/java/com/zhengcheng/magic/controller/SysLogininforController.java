package com.zhengcheng.magic.controller;


import com.zhengcheng.common.domain.PageResult;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.magic.domain.SysLoginLogEntity;
import com.zhengcheng.magic.service.SysLoginLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统访问记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController {
    @Autowired
    private SysLoginLogService logininforService;


    @GetMapping(value = "/list", name = "登录日志-分类列表")
    public Result list(SysLoginLogEntity logininfor) {
        PageResult<SysLoginLogEntity> page = logininforService.selectLogininforPage(logininfor);
        return Result.ok().put(page);
    }

    @DeleteMapping(value = "/{infoIds}", name = "登录日志-删除")
    public Result remove(@PathVariable Long[] infoIds) {
        return Result.ok(logininforService.deleteLogininforByIds(infoIds));
    }

    @DeleteMapping(value = "/clean", name = "登录日志-清空")
    public Result clean() {
        logininforService.cleanLogininfor();
        return Result.ok();
    }

}
